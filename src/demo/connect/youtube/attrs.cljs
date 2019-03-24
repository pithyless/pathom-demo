(ns demo.connect.youtube.attrs)


(def aliases
  {:youtube.video.snippet/channel-id                   :youtube.channel/id
   :youtube.playlist.snippet/channel-id                :youtube.channel/id
   :youtube.playlist-item.content-details/video-id     :youtube.video/id
   :youtube.playlist-item.snippet.resource-id/video-id :youtube.video/id
   :youtube.playlist-item.snippet/channel-id           :youtube.channel/id
   :youtube.playlist-item.snippet/playlist-id          :youtube.playlist/id})


(def video-snippet
  [:youtube.video/id
   :youtube.video.snippet.localized/description
   :youtube.video.snippet.localized/title
   :youtube.video.snippet/category-id
   :youtube.video.snippet/channel-id
   :youtube.video.snippet/channel-title
   :youtube.video.snippet/default-audio-language
   :youtube.video.snippet/default-language
   :youtube.video.snippet/description
   :youtube.video.snippet/published-at
   :youtube.video.snippet/title
   :youtube.video.snippet.thumbnails.default/height
   :youtube.video.snippet.thumbnails.default/url
   :youtube.video.snippet.thumbnails.default/width
   :youtube.video.snippet.thumbnails.high/height
   :youtube.video.snippet.thumbnails.high/url
   :youtube.video.snippet.thumbnails.high/width])


(def video-statistics
  [:youtube.video.statistics/view-count
   :youtube.video.statistics/like-count
   :youtube.video.statistics/dislike-count
   :youtube.video.statistics/favorite-count
   :youtube.video.statistics/comment-count])


(def video-player
  [:youtube.video.player/embed-height
   :youtube.video.player/embed-html
   :youtube.video.player/embed-width])


(def video-topic-details
  [:youtube.video.topic-details/relevant-topic-ids
   :youtube.video.topic-details/topic-categories
   :youtube.video.topic-details/topic-ids])


(def video-file-details
  [:youtube.video.file-details/bitrate-bps
   :youtube.video.file-details/creation-time
   :youtube.video.file-details/file-name
   :youtube.video.file-details/file-size
   :youtube.video.file-details/file-type])


(def channel-snippet
  [:youtube.channel/id
   :youtube.channel.snippet/title
   :youtube.channel.snippet/description
   :youtube.channel.snippet/custom-url
   :youtube.channel.snippet/published-at

   :youtube.channel.snippet.thumbnails.default/height
   :youtube.channel.snippet.thumbnails.default/url
   :youtube.channel.snippet.thumbnails.default/width
   :youtube.channel.snippet.thumbnails.high/height
   :youtube.channel.snippet.thumbnails.high/url
   :youtube.channel.snippet.thumbnails.high/width])


(def channel-statistics
  [:youtube.channel.statistics/view-count
   :youtube.channel.statistics/comment-count
   :youtube.channel.statistics/subscriber-count
   :youtube.channel.statistics/hidden-subscriber-count
   :youtube.channel.statistics/video-count])


(def playlist-details
  [:youtube.playlist/id
   :youtube.playlist.content-details/item-count
   :youtube.playlist.snippet.localized/description
   :youtube.playlist.snippet.localized/title
   :youtube.playlist.snippet/channel-id
   :youtube.playlist.snippet/channel-title
   :youtube.playlist.snippet/default-language
   :youtube.playlist.snippet/description
   :youtube.playlist.snippet/published-at
   :youtube.playlist.snippet/title
   :youtube.playlist/kind])


(def playlist-item-details
  [:youtube.playlist-item/id
   :youtube.playlist-item.content-details/end-at
   :youtube.playlist-item.content-details/note
   :youtube.playlist-item.content-details/start-at
   :youtube.playlist-item.content-details/video-id
   :youtube.playlist-item.snippet.resource-id/kind
   :youtube.playlist-item.snippet.resource-id/video-id
   :youtube.playlist-item.snippet/channel-id
   :youtube.playlist-item.snippet/channel-title
   :youtube.playlist-item.snippet/description
   :youtube.playlist-item.snippet/playlist-id
   :youtube.playlist-item.snippet/position
   :youtube.playlist-item.snippet/published-at
   :youtube.playlist-item.snippet/title
   :youtube.playlist-item.status/privacy-status
   :youtube.playlist-item/kind])
