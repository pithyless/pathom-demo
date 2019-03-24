(ns demo.connect.youtube.util
  (:require [clojure.string :as str]
            [com.wsscode.common.async-cljs :refer [<? go-catch]]
            [com.wsscode.pathom.core :as p]
            [com.wsscode.pathom.connect :as pc]
            [com.wsscode.pathom.diplomat.http :as http]
            [goog.string :as gstr]
            [clojure.set :as set]))


(def base-uri "https://www.googleapis.com/youtube/v3/")


(defn output->blank-entity
  [output]
  (into {}
        (map (fn [x]
               (if (map? x)
                 [(first (keys x)) (output->blank-entity (first (vals x)))]
                 [x nil])))
        output))


(defn query-string [params]
  (str/join "&" (map (fn [[k v]] (str (name k) "=" v)) params)))


(defn youtube-query-part [prefix keyword]
  (let [ns (namespace keyword)]
    (when (str/starts-with? ns prefix)
      (-> ns (str/split ".") (->> (drop 2)) first))))


(defn request-parts [env prefix]
  (let [query           (or (::parts-query env) (::p/parent-query env))
        ;; _               (pr query)
        ;; _               (pr (pc/project-query-attributes env query))
        allowed-attrs   (set (get-in env [::pc/resolver-data ::pc/output]))
        requested-attrs (pc/project-query-attributes env query)
        ;; _               (pr [query allowed-attrs requested-attrs])
        parts           (->> (set/intersection allowed-attrs requested-attrs)
                             (into #{} (comp (keep (partial youtube-query-part prefix))
                                             (map gstr/toCamelCase))))]
    (if (seq parts)
      (str/join "," (into parts ["snippet"]))
      "id,snippet")))


(defn youtube-params [m]
  (into {} (keep (fn [[k v]]
                   (when (str/starts-with? (str k) ":youtube.param/")
                     [(gstr/toCamelCase (name k)) v]))) m))


(defn youtube-api [{:keys [:demo.connect.youtube/access-token] :as env} path data]
  (if access-token
    (let [data (merge {:key access-token}
                 (youtube-params (-> env :ast :params))
                 data)]
      (go-catch
        (-> (http/request env
              (str base-uri path "?" (query-string data))
              {::http/accept ::http/json}) <?
            ::http/body)))
    (do
      (js/console.error "Missing youtube token")
      (throw (ex-info "Youtube token not available" {})))))


(defn youtube->output [prefix resource-schema]
  (reduce-kv
    (fn [out k v]
      (cond
        (map? v)
        (into out (youtube->output (str prefix "." (gstr/toSelectorCase (name k))) v))

        (or (string? v)
            (and (vector? v) (string? (first v))))
        (conj out (keyword prefix (gstr/toSelectorCase (name k))))

        (and (vector? v) (map? (first v)))
        (conj out {(keyword prefix (gstr/toSelectorCase (name k)))
                   (youtube->output (str prefix "." (gstr/toSelectorCase (name k)))
                     (first v))})

        :else
        (do
          (println "CANT HANDLE" (pr-str [k v]))
          out)))
    []
    resource-schema))


(defn adapt-recursive
  "Pull some key, updating the namespaces of it"
  [x ns]
  (reduce-kv
    (fn [out k v]
      (let [k' (keyword ns (gstr/toSelectorCase (name k)))]
        (cond
          (map? v)
          (into out (adapt-recursive v (str ns "." (gstr/toSelectorCase (name k)))))

          :else
          (assoc out k' v))))
    {}
    x))
