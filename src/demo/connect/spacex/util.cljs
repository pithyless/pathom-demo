(ns demo.connect.spacex.util
  (:require [clojure.string :as str]
            [com.wsscode.common.async-cljs :refer [<? go-catch]]
            [com.wsscode.pathom.diplomat.http :as http]))


(def base-uri "https://api.spacexdata.com/v3/")


(defn query-string [params]
  (str/join "&" (map (fn [[k v]] (str (name k) "=" v)) params)))


(defn spacex-api [env path data]
  (go-catch
   (-> (http/request env
                     (str base-uri path "?" (query-string data))
                     {::http/accept ::http/json})
       <?
       ::http/body)))


(defn adapt-recursive
  "Pull some key, updating the namespaces of it"
  [x ns]
  (reduce-kv
   (fn [out k v]
     (let [k' (keyword ns (str/replace (name k) "_" "-"))]
       (cond
         (map? v)
         (into out (adapt-recursive v (str ns "." (str/replace (name k) "_" "-"))))

         :else
         (assoc out k' v))))
   {}
   x))
