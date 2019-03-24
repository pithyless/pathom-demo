(ns demo.connect.youtube.plugin
  (:require [com.wsscode.pathom.connect :as pc]
            [demo.connect.youtube.resolvers :as resolvers]))

(defn init []
  {::pc/register [(resolvers/resolvers)]})
