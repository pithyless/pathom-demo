(ns demo.connect.spacex.plugin
  (:require [com.wsscode.pathom.connect :as pc]
            [demo.connect.spacex.resolvers :as resolvers]))

(defn init []
  {::pc/register [(resolvers/resolvers)]})
