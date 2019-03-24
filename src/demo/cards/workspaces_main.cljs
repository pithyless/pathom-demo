(ns ^:dev/always demo.cards.workspaces-main
  (:require [com.wsscode.pathom.connect :as pc]
            [com.wsscode.pathom.core :as p]
            [nubank.workspaces.core :as ws]
            [com.wsscode.pathom.viz.workspaces :as pvw]
            [com.wsscode.pathom.diplomat.http :as p.http]
            [com.wsscode.pathom.diplomat.http.fetch :as p.http.fetch]
            [demo.connect.spacex.plugin :as spacex.plugin]
            [demo.connect.youtube.plugin :as youtube.plugin]
            [demo.secret :as secret]
            [demo.cards.shared-workspaces]))


(pc/defresolver my-videos [env input]
  {::pc/output [{:my-videos [:youtube.video/id]}]}
  {:my-videos [{:youtube.video/id "6_mbxaRDA-s"}
               {:youtube.video/id "l5ML_4WnAWg"}
               {:youtube.video/id "oo-7mN9WXTw"}]})


(defn app-registry []
  [my-videos])


(defn parser []
  (p/parallel-parser
   {::p/env     {::p/reader                         [p/map-reader pc/parallel-reader pc/ident-reader p/env-placeholder-reader
                                                     pc/all-readers]
                 ::p/placeholder-prefixes           #{">"}
                 ::pc/resolver-dispatch             pc/resolver-dispatch-embedded
                 ::pc/mutate-dispatch               pc/mutation-dispatch-embedded
                 ::p.http/driver                    p.http.fetch/request-async
                 :demo.connect.youtube/access-token secret/youtube-token
                 ::db                               (atom {})}
    ::p/mutate  pc/mutate-async
    ::p/plugins [p/error-handler-plugin
                 p/request-cache-plugin
                 p/trace-plugin
                 (pc/connect-plugin {::pc/register (app-registry)})
                 (youtube.plugin/init)
                 (spacex.plugin/init)]}))


(ws/defcard simple-parser-demo
  (pvw/pathom-card {::pvw/parser (parser)}))


(ws/mount)
