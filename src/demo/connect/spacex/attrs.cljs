(ns demo.connect.spacex.attrs)


(def aliases
  {:spacex.launch.links/youtube-id :youtube.video/id})


(def launch
  [:spacex.launch/id
   :spacex.launch/launch-year
   :spacex.launch.links/youtube-id

   :spacex.launch.launch-site/site-id
   :spacex.launch.launch-site/site-name
   :spacex.launch.launch-site/site-name-long
   :spacex.launch.rocket.fairings/recovered
   :spacex.launch.rocket.fairings/recovery-attempt
   :spacex.launch.rocket.fairings/reused
   :spacex.launch.rocket.fairings/ship
   :spacex.launch.rocket.first-stage/cores
   :spacex.launch.rocket.second-stage/block
   :spacex.launch.rocket.second-stage/payloads
   :spacex.launch.rocket/rocket-id
   :spacex.launch.rocket/rocket-name
   :spacex.launch.rocket/rocket-type
   :spacex.launch.telemetry/flight-club
   :spacex.launch/details
   :spacex.launch/flight-number
   :spacex.launch/is-tentative
   :spacex.launch/launch-date-local
   :spacex.launch/launch-date-unix
   :spacex.launch/launch-date-utc
   :spacex.launch/launch-success
   :spacex.launch/launch-window
   :spacex.launch/launch-year
   :spacex.launch/mission-id
   :spacex.launch/mission-name
   :spacex.launch/ships
   :spacex.launch/static-fire-date-unix
   :spacex.launch/static-fire-date-utc
   :spacex.launch/tbd
   :spacex.launch/tentative-max-precision
   :spacex.launch/upcoming])
