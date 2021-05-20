#!/usr/bin/env bash

set -e

[ -n "$PROJECT_VERSION" ]

jq ".properties.\"jepyter.version\" = \"$PROJECT_VERSION\"" jep.json | tee jep-release.json