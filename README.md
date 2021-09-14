<!--
 ___ _            _ _    _ _    __
/ __(_)_ __  _ __| (_)__(_) |_ /_/
\__ \ | '  \| '_ \ | / _| |  _/ -_)
|___/_|_|_|_| .__/_|_\__|_|\__\___|
            |_| 
-->
![](https://docs.simplicite.io//logos/logo250.png)
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
| `monHeaAppVersion`                                           | char(10)                                 |          |           |          | -                                                                                |
| `monHeaSessions`                                             | int(10)                                  |          |           |          | -                                                                                |
| `monHeaEnabledUsers`                                         | int(10)                                  |          |           |          | -                                                                                |
| `monHeaTotalUsers`                                           | int(10)                                  |          |           |          | -                                                                                |
| `monHeaFreeDisk`                                             | int(10)                                  |          |           |          | -                                                                                |
| `monHeaUsableDisk`                                           | int(10)                                  |          |           |          | -                                                                                |
| `monHeaTotalDisk`                                            | int(10)                                  |          |           |          | -                                                                                |
| `monHeaDiskUsage`                                            | float(5, 2)                              |          |           |          | -                                                                                |
| `monHeaFreeHeap`                                             | int(10)                                  |          |           |          | -                                                                                |
| `monHeaHeapSize`                                             | int(10)                                  |          |           |          | -                                                                                |
| `monHeaMaxHeapSize`                                          | int(10)                                  |          |           |          | -                                                                                |
| `monHeaTotalFreeSize`                                        | int(10)                                  |          |           |          | -                                                                                |
| `monHeaHeapUsage`                                            | float(5, 2)                              |          | yes       |          | -                                                                                |
| `monHeaGrantCache`                                           | int(10)                                  |          |           |          | -                                                                                |
| `monHeaMaxGrantCache`                                        | int(10)                                  |          |           |          | -                                                                                |
| `monHeaGrantCacheRatio`                                      | float(5, 2)                              |          |           |          | -                                                                                |
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




