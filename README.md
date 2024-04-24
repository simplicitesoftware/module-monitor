<!--
 ___ _            _ _    _ _    __
/ __(_)_ __  _ __| (_)__(_) |_ /_/
\__ \ | '  \| '_ \ | / _| |  _/ -_)
|___/_|_|_|_| .__/_|_\__|_|\__\___|
            |_| 
-->
![](https://platform.simplicite.io/logos/standard/logo250.png)
* * *

`Monitor` module definition
===========================



`MonCntPrj` business object definition
--------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `monCntprjCntId` link to **`MonContact`**                    | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `monCntprjCntId.monCntEmail`_                          | _email(100)_                             |          |           |          | -                                                                                |
| `monCntprjPrjId` link to **`MonProject`**                    | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `monCntprjPrjId.monPrjName`_                           | _char(50)_                               |          |           |          | -                                                                                |

`MonContact` business object definition
---------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `monCntEmail`                                                | email(100)                               | yes*     | yes       |          | -                                                                                |

`MonHealth` business object definition
--------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `monHeaInstId` link to **`MonInstance`**                     | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `monHeaInstId.monInstUrl`_                             | _url(100)_                               |          |           |          | -                                                                                |
| `monHeaDate`                                                 | datetime                                 | yes*     |           |          | -                                                                                |
| `monHeaStatus`                                               | enum(2) using `MONHEASTATUS` list        |          |           |          | -                                                                                |
| `monHeaVersion`                                              | char(100)                                |          |           |          | -                                                                                |
| `monHeaBuiltOn`                                              | char(250)                                |          |           |          | -                                                                                |
| `monHeaDatabasePatchLevel`                                   | char(100)                                |          |           |          | -                                                                                |
| `monHeaAppVersion`                                           | char(255)                                |          |           |          | -                                                                                |
| `monHeaSessions`                                             | int(10)                                  |          |           |          | -                                                                                |
| `monHeaEnabledUsers`                                         | int(10)                                  |          |           |          | -                                                                                |
| `monHeaTotalUsers`                                           | int(10)                                  |          |           |          | -                                                                                |
| `monHeaFreeHeap`                                             | int(10)                                  |          |           |          | -                                                                                |
| `monHeaHeapSize`                                             | int(10)                                  |          |           |          | -                                                                                |
| `monHeaMaxHeapSize`                                          | int(10)                                  |          |           |          | -                                                                                |
| `monHeaTotalFreeSize`                                        | int(10)                                  |          |           |          | -                                                                                |
| `monHeaHeapUsage`                                            | float(5, 2)                              |          | yes       |          | -                                                                                |
| `monHeaGrantCache`                                           | int(10)                                  |          |           |          | -                                                                                |
| `monHeaObjectCache`                                          | int(10)                                  |          |           |          | -                                                                                |
| `monHeaMaxObjectCache`                                       | int(10)                                  |          |           |          | -                                                                                |
| `monHeaObjectCacheRatio`                                     | float(5, 2)                              |          |           |          | -                                                                                |
| `monHeaObjects`                                              | int(5)                                   |          |           |          | -                                                                                |
| `monHeaAttributes`                                           | int(5)                                   |          |           |          | -                                                                                |
| `monHeaFunctions`                                            | int(5)                                   |          |           |          | -                                                                                |
| `monHeaGroups`                                               | int(5)                                   |          | yes       |          | -                                                                                |
| `monHeaStates`                                               | int(5)                                   |          |           |          | -                                                                                |
| `monHeaConstraints`                                          | int(5)                                   |          |           |          | -                                                                                |
| `monHeaCrosstables`                                          | int(5)                                   |          |           |          | -                                                                                |
| `monHeaTemplates`                                            | int(5)                                   |          |           |          | -                                                                                |
| `monHeaScripts`                                              | int(5)                                   |          |           |          | -                                                                                |
| `monHeaActions`                                              | int(5)                                   |          |           |          | -                                                                                |
| `monHeaPublications`                                         | int(5)                                   |          |           |          | -                                                                                |
| `monHeaLastLogin`                                            | datetime                                 |          |           |          | -                                                                                |
| `monHeaActiveUsers`                                          | int(10)                                  |          | yes       |          | -                                                                                |
| `monHeaInactiveUsers`                                        | int(10)                                  |          | yes       |          | -                                                                                |
| `monHeaPendingUsers`                                         | int(10)                                  |          | yes       |          | -                                                                                |
| `monHeaWebserviceUsers`                                      | int(10)                                  |          | yes       |          | -                                                                                |

### Lists

* `MONHEASTATUS`
    - `OK` OK
    - `KO` KO

`MonInstance` business object definition
----------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `monInstUrl`                                                 | url(100)                                 | yes*     | yes       |          | -                                                                                |
| `monInstPollFreq`                                            | int(10)                                  |          | yes       |          | -                                                                                |
| `monInstMemoryUsage`                                         | enum(3) using `MON_INST_USAGE_SIZE` list |          |           |          | -                                                                                |
| `monInstUserUsage`                                           | enum(3) using `MON_INST_USAGE_SIZE` list |          |           |          | -                                                                                |
| `monInstModelSize`                                           | enum(3) using `MON_INST_USAGE_SIZE` list |          |           |          | -                                                                                |

### Lists

* `MON_INST_USAGE_SIZE`
    - `XS` XS
    - `S` S
    - `M` M
    - `L` L
    - `XXL` XXL

`MonProject` business object definition
---------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `monPrjName`                                                 | char(50)                                 | yes*     | yes       |          | -                                                                                |

`MonProjectInstance` business object definition
-----------------------------------------------

Simplicité instance

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `monInstUrl`                                                 | url(100)                                 | yes*     | yes       |          | -                                                                                |
| `monInstPrjId` link to **`MonProject`**                      | id                                       |          | yes       |          | -                                                                                |
| _Ref. `monInstPrjId.monPrjName`_                             | _char(50)_                               |          |           |          | -                                                                                |

### Custom actions

* `callInstance`: 
* `callInstances`: 

`MonInstGraphExt` external object definition
--------------------------------------------




