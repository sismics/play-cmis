[![GitHub release](https://img.shields.io/github/release/sismics/play-cmis.svg?style=flat-square)](https://github.com/sismics/play-cmis/releases/latest)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# play-cmis plugin

This plugin adds [CMIS](https://en.wikipedia.org/wiki/Content_Management_Interoperability_Services) support to Play! Framework 1 applications.

# Features

# How to use

####  Add the dependency to your `dependencies.yml` file

```
require:
    - cmis -> cmis 1.1.0

repositories:
    - sismicsNexusRaw:
        type: http
        artifact: "https://nexus.sismics.com/repository/sismics/[module]-[revision].zip"
        contains:
            - cmis -> *

```
####  Run the `play deps` command
####  Add the routes to your `routes` file

```
# CMIS routes
*       /               module:cmis
```

# License

This software is released under the terms of the Apache License, Version 2.0. See `LICENSE` for more
information or see <https://opensource.org/licenses/Apache-2.0>.
