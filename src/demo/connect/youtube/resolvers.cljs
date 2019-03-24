(ns demo.connect.youtube.resolvers
  (:require [com.wsscode.pathom.connect :as pc]
            [demo.connect.youtube.attrs :as attrs]
            [demo.connect.youtube.api :as api]))


(defn mergev [colls]
  (reduce into [] colls))


(def video-by-id-extended
  (pc/resolver `video-by-id
    {::pc/input  #{:youtube.video/id}
     ::pc/output (mergev [attrs/video-snippet
                          attrs/video-statistics
                          attrs/video-player
                          attrs/video-topic-details
                          attrs/video-file-details])
     ::pc/batch? true}
    (pc/batch-resolver
      (fn [env ids]
        (api/batch-fetch-video env ids)))))


(def video-by-id
  (pc/resolver `video-by-id2
    {::pc/input  #{:youtube.video/id}
     ::pc/output (mergev [attrs/video-snippet
                          attrs/video-statistics
                          attrs/video-player
                          attrs/video-topic-details])
     ::pc/batch? true}
    (pc/batch-resolver
      (fn [env ids]
        (api/batch-fetch-video env ids)))))


(def channel-by-id
  (pc/resolver `channel-by-id
    {::pc/input  #{:youtube.channel/id}
     ::pc/output (mergev [attrs/channel-snippet
                          attrs/channel-statistics])
     ::pc/batch? true}
    (pc/batch-resolver
      (fn [env ids]
        (api/batch-fetch-channel env ids)))))


(pc/defresolver channel-by-username [env input]
  {::pc/input  #{:youtube.user/username}
   ::pc/output (mergev [attrs/channel-snippet
                        attrs/channel-statistics])}
  (api/single-fetch-channel env input))


(def playlist-by-id
  (pc/resolver `playlist-by-id
    {::pc/input   #{:youtube.playlist/id}
     ::pc/output  (mergev [attrs/playlist-details])
     ::pc/batch?  true}
    (pc/batch-resolver
      (fn [env ids]
        (api/batch-fetch-playlist env ids)))))


(pc/defresolver playlist-items-by-id [env input]
  {::pc/input  #{:youtube.playlist/id}
   ::pc/output [{:youtube.playlist/items attrs/playlist-item-details}]}
  (api/single-fetch-playlist-items-by-id env attrs/playlist-item-details input))


(pc/defresolver playlists-by-channel [env input]
  {::pc/input  #{:youtube.channel/id}
   ::pc/output [{:youtube.channel/playlists attrs/playlist-details}]}
  (api/single-fetch-playlist-by-channel-id env attrs/playlist-details input))


(defn resolver-alias [from to]
  (pc/resolver (symbol (munge (str from "-" to)))
    {::pc/input #{from} ::pc/output [to]}
    (fn [_ input] {to (get input from)})))


(defn alias-resolvers [aliases]
  (mapv (fn [[k v]] (resolver-alias k v)) aliases))


(defn resolvers []
  (mergev [[video-by-id-extended
            video-by-id
            channel-by-id
            channel-by-username
            playlist-by-id
            playlist-items-by-id
            playlists-by-channel
            ]
           (alias-resolvers attrs/aliases)]))
