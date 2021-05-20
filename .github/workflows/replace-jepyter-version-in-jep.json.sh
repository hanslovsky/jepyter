#!/usr/bin/env bash

set -e

[ -n "$PROJECT_VERSION" ]

jq ".properties.\"jepyter.version\" = \"jepyter-$PROJECT_VERSION\"" jep.json | tee jep-release.json