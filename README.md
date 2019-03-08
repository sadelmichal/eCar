[![Build Status](https://travis-ci.com/sadelmichal/eCar.svg?branch=master)](https://travis-ci.com/sadelmichal/eCar)
[![codecov](https://codecov.io/gh/sadelmichal/eCar/branch/master/graph/badge.svg)](https://codecov.io/gh/sadelmichal/eCar)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2cedb11dce3a4d04be2e48f002ca7b40)](https://app.codacy.com/app/sadelmichal/eCar?utm_source=github.com&utm_medium=referral&utm_content=sadelmichal/eCar&utm_campaign=Badge_Grade_Dashboard)
[![Requirements Status](https://requires.io/github/sadelmichal/eCar/requirements.svg?branch=master)](https://requires.io/github/sadelmichal/eCar/requirements/?branch=master)

# eCar

eCar company is going to introduce new way pricing in their new, successful backoffice supporting electric vehicles charging network. 

## Requirements
  -  [x] Ability to define and calculate a price via API.  
  -  [x] A price definition will contain price per minute and can be defined for specific duration, e.g. one price before noon and different one afternoon 
  -  [x] A calculation input will contain start and end datetime of charging process and customer id 
  -  [x] For VIP customers we will grant 10% discount 
  -  [x] An application should be able to be launched from command line with single command like "java –jar...." 
  -  [x] A solution should require no additional software to be installed except for JRE 8

## Assumptions
  -  Price definition **can** have a time range, but it is not required.
  -  If price definition does not have a time specified, it is assumed it applies all day long *00:00 - 23:59:59* and becomes default price
  -  Only one *default price* can be defined
  -  If price definition is fully defined (has time range) then it has priority before default one
  -  System rejects a price definition if there is already price in the system which overlaps with the new one
  -  System is in the same time zone than the customer

## Additional methods
  -  Price and customers can be deleted 
  -  Price and customers can listed 

## Deployment
System is continually delivered to *Azure*. It can be reached at [eCar](https://ecar-sadel.azurewebsites.net/)               
