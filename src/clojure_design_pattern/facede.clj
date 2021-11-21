(ns clojure-design-pattern.facede)
(ns application.old-servlet
  (:require [application.request-extractor :as re])
  (:require [application.request-validator :as rv])
  (:require [application.transformer :as t])
  (:require [application.response-builder :as rb]))

(defn service [request]
  (-> request
      (re/extract)
      (rv/validate)
      (t/transform)
      (rb/build)))


(ns application.facade
  (:require [application.request-extractor :as re])
  (:require [application.request-validator :as rv])
  (:require [application.transformer :as t])
  (:require [application.response-builder :as rb]))

(defn request-extract [request]
  (re/extract request))


(defn request-validate [request]
  (rv/validate request))


(defn request-transform [request]
  (t/transform request))

(defn response-build [request]
  (rb/build request))

(ns application.old-servlet
  (:use [application.facade]))

(defn service [request]
  (-> request
      (request-extract)
      (request-validate)
      (request-transform)
      (request-build)))
