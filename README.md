ILCD Ecospold 2 Conversor
=========================

This repository contains the source code for a adhoc format conversion library.
It is forked and adapted from
[GreenDelta/olca-conversion-service](https://github.com/GreenDelta/olca-conversion-service/).

USAGE
-----

Instantiate the Converter class with its default constructor:

```
Converter conv = new Converter("/path/to/converter/workspace");
conv.converter(spoldFile, outputILCDfile);
```

You must have and appropriate workspace in `/path/to/converter/workspace`. An
example is provided in `/example/workspace`.  This workspace must have the following structure:

* workspace
    - refsystems
        + es2
            - data.zip
    - cache

The file data.zip, which must contain data from a reference system in openLCA
JSON-LD format, is necessary for proper conversion. This file may be obtained
by openLCA export. For more information, see
[GreenDelta/olca-conversion-service](https://github.com/GreenDelta/olca-conversion-service/).

License
-------
This source code is licensed under the [Mozilla Public License, v.
2.0](http://www.mozilla.org/MPL/2.0/).  Please see the LICENSE.txt file in the
root directory of the source code.
