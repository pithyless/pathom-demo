(ns demo.connect.spacex.api
  (:require [com.wsscode.common.async-cljs :refer [<? go-catch]]
            [demo.connect.spacex.util :as util]))


(defn adapt-launch [launch]
  (util/adapt-recursive launch "spacex.launch"))


(defn single-fetch-launch [env input]
  (let [{:keys [:spacex.launch/id]} input]
    (go-catch
     (-> (util/spacex-api env (str "launches/" id) {})
         <?
         adapt-launch))))
