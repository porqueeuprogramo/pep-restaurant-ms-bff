{{/*
Expand the name of the chart.
*/}}
{{- define "pep-restaurant-ms-bff.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "pep-restaurant-ms-bff.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "pep-restaurant-ms-bff.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Common labels
*/}}
{{- define "pep-restaurant-ms-bff.labels" -}}
helm.sh/chart: {{ include "pep-restaurant-ms-bff.chart" . }}
{{ include "pep-restaurant-ms-bff.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Service labels
*/}}
{{- define "pep-restaurant-ms-bff.serviceLabels" -}}
app: {{ include "pep-restaurant-ms-bff.name" . }}
{{- end }}

{{/*
Selector match labels
*/}}
{{- define "pep-restaurant-ms-bff.selectorMatchLabels" -}}
app: {{ include "pep-restaurant-ms-bff.name" . }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "pep-restaurant-ms-bff.selectorLabels" -}}
stage: {{ .Values.app.properties.environment }}
app: {{ include "pep-restaurant-ms-bff.name" . }}
app.kubernetes.io/name: {{ include "pep-restaurant-ms-bff.name" . }}
app.kubernetes.io/version: {{ .Chart.AppVersion }}
app.kubernetes.io/part-of: APP-26338
{{- end }}

{{/*
Create the name of the service account to use
*/}}
{{- define "pep-restaurant-ms-bff.serviceAccountName" -}}
{{- if .Values.serviceAccount.create }}
{{- default (include "pep-restaurant-ms-bff.fullname" .) .Values.serviceAccount.name }}
{{- else }}
{{- default "default" .Values.serviceAccount.name }}
{{- end }}
{{- end }}


{{- define "getSecret" -}}
{{- $secretName := index . 0 -}}
{{- $key := index . 1 -}}
{{- printf "%s" (include "tpl" $secretName $key) | b64enc | quote -}}
{{- end -}}