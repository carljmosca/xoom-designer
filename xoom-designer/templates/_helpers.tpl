{{/*
Expand the name of the chart.
*/}}
{{- define "xoom-designer.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "xoom-designer.fullname" -}}
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
{{- define "xoom-designer.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Common labels
*/}}
{{- define "xoom-designer.labels" -}}
helm.sh/chart: {{ include "xoom-designer.chart" . }}
{{ include "xoom-designer.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "xoom-designer.selectorLabels" -}}
app.kubernetes.io/name: {{ include "xoom-designer.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{/*
Create the name of the service account to use
*/}}
{{- define "xoom-designer.serviceAccountName" -}}
{{- if .Values.serviceAccount.create }}
{{- default (include "xoom-designer.fullname" .) .Values.serviceAccount.name }}
{{- else }}
{{- default "default" .Values.serviceAccount.name }}
{{- end }}
{{- end }}

{{/*
Environment variables for Xoom-Designer
*/}}
{{- define "xoom-designer.environment" -}}
env:
  - name: VLINGO_XOOM_DESIGNER_SERVER_PORT
    value: "19090"
  - name: VLINGO_XOOM_DESIGNER_ENV
    value: CONTAINER
  - name: SCHEMATA_SERVICE_NAME
    value: xoom-schemata
  - name: SCHEMATA_SERVICE_PORT
    value: "9019"
{{- end -}}

{{/*
Environment variables for Xoom-Schemata
*/}}
{{- define "xoom-schemata.environment" -}}
env:
  - name: XOOM_SCHEMATA_PORT
    value: "9019"
  - name: XOOM_ENV
    value: env
  - name: XOOM_SCHEMATA_DB_URL
    value: jdbc:postgresql://schemata-db/
{{- end -}}    

{{/*
Environment variables for Xoom-Schemata
*/}}
{{- define "database.environment" -}}
env:
  - name: POSTGRESQL_USERNAME
    valueFrom:
      secretKeyRef:
        name: xoom-designer-env-secret
        key: sql-user
        optional: false
  - name: POSTGRESQL_PASSWORD
    valueFrom:
      secretKeyRef:
        name: xoom-designer-env-secret
        key: sql-password
        optional: false
  - name: PGPASSWORD
    valueFrom:
      secretKeyRef:
        name: xoom-designer-env-secret
        key: sql-password
        optional: false
  - name: POSTGRESQL_POSTGRES_PASSWORD
    valueFrom:
      secretKeyRef:
        name: xoom-designer-env-secret
        key: sql-password
        optional: false
  - name: POSTGRESQL_DATABASE
    value: {{ .Values.sqlDatabase }}
  - name: SQL_HOST
    value: schemata-db
  - name: SQL_PORT
    value: "5432"
  - name: SQL_USER
    value: postgress
{{- end -}}
