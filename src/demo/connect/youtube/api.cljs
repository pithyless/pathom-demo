(ns demo.connect.youtube.api
  (:require [clojure.string :as str]
            [com.wsscode.common.async-cljs :refer [<? go-catch]]
            [com.wsscode.pathom.connect :as pc]
            [com.wsscode.pathom.core :as p]
            [demo.connect.youtube.util :as util]))


(defn adapt-video [video]
  (util/adapt-recursive video "youtube.video"))


(defn adapt-channel [channel]
  (util/adapt-recursive channel "youtube.channel"))


(defn adapt-playlist [playlist]
  (util/adapt-recursive playlist "youtube.playlist"))


(defn adapt-playlist-item [playlist-item]
  (util/adapt-recursive playlist-item "youtube.playlist-item"))


(defn batch-fetch-playlist [env ids]
  (go-catch
   (->> (util/youtube-api env "playlists"
                         {:id   (str/join "," (map :youtube.playlist/id ids))
                          :part (util/request-parts env "youtube.playlist")}) <?
        :items
        (mapv adapt-playlist)
        (pc/batch-restore-sort {::pc/inputs ids
                                ::pc/key    :youtube.playlist/id}))))


(defn single-fetch-playlist-by-channel-id [env playlist-output input]
  (let [{:keys [:youtube.channel/id]} input]
    (go-catch
     (->> (util/youtube-api env "playlists"
                            {:channelId id
                             :part      (util/request-parts
                                         (assoc env ::util/parts-query (-> env :ast :query)
                                                ::p/entity (util/output->blank-entity playlist-output))
                                         "youtube.playlist")}) <?
          :items
          (mapv adapt-playlist)
          (hash-map :youtube.channel/playlists)))))


(defn single-fetch-playlist-items-by-id [env playlist-item-output input]
  (let [{:keys [:youtube.playlist/id]} input]
    (go-catch
     (->> (util/youtube-api env "playlistItems"
                            {:playlistId id
                             :part       (util/request-parts
                                          (assoc env ::util/parts-query (-> env :ast :query)
                                                 ::p/entity (util/output->blank-entity playlist-item-output))
                                          "youtube.playlist-item")}) <?
          :items
          (mapv adapt-playlist-item)
          (hash-map :youtube.playlist/items)))))


(defn batch-fetch-video [env ids]
  (go-catch
   (->> (util/youtube-api env "videos"
                          {:id   (str/join "," (map :youtube.video/id ids))
                           :part (util/request-parts env "youtube.video")}) <?
        :items
        (mapv adapt-video)
        (pc/batch-restore-sort {::pc/inputs ids
                                ::pc/key    :youtube.video/id}))))


(defn batch-fetch-channel [env ids]
  (go-catch
   (->> (util/youtube-api env "channels"
                         {:id   (str/join "," (map :youtube.channel/id ids))
                          :part (util/request-parts env "youtube.channel")}) <?
        :items
        (mapv adapt-channel)
        (pc/batch-restore-sort {::pc/inputs ids
                                ::pc/key    :youtube.channel/id}))))


(defn single-fetch-channel [env input]
  (let [{:keys [:youtube.user/username]} input]
    (go-catch
     (-> (util/youtube-api env "channels"
                           {:forUsername username
                            :part        (util/request-parts env "youtube.channel")}) <?
         :items
         first
         adapt-channel))))
