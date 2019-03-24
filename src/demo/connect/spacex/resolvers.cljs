(ns demo.connect.spacex.resolvers
  (:require [com.wsscode.pathom.connect :as pc]
            [demo.connect.spacex.attrs :as attrs]
            [demo.connect.spacex.api :as api]))


(defn mergev [colls]
  (reduce into [] colls))


(def launch-by-id
  (pc/defresolver launch-by-id [env input]
    {::pc/input  #{:spacex.launch/id}
     ::pc/output (mergev [attrs/launch])}
    (api/single-fetch-launch env input)))


(defn resolver-alias [from to]
  (pc/resolver (symbol (munge (str from "-" to)))
    {::pc/input #{from} ::pc/output [to]}
    (fn [_ input] {to (get input from)})))


(defn alias-resolvers [aliases]
  (mapv (fn [[k v]] (resolver-alias k v)) aliases))


(defn resolvers []
  (mergev [[launch-by-id]
           (alias-resolvers attrs/aliases)]))
