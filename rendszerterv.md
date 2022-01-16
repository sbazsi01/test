
# Rendszerterv

## 1. A rendszer célja

## 2. Projektterv

### 2.1 Projektszerepkörök, felelősségek
-   Tóth Zsolt: A projekt irányítása, az akadályok feltárása, az elvégzett és a következő feladatok átbeszélése

Üzleti szereplők (Stakeholders):

-   Megrendelő: Klasszikus járművekkel kereskedő.

### 2.2 Projektmunkások és felelősségeik

Backend:

-   Csik Patrik
-   Kiss Olga
-   Molnár Viktor
-   Sulyok Balázs
-  Szabó Zoltán Feladatuk a funkciók létrehozása, unit-tesztek megírása és futtatása, adatbazis kapcsolat felépítése, adatok átvitele, ellenőrzése a kliens és az adatbázis között.

### 2.3 Ütemterv

Sprint 1 3-4.hét
Sprint 2 5-6.hét
Sprint 3 7-8.hét
Sprint 4 9-10.hét
Sprint 5 11-12.hét
Bemutatás 13.hét

### 2.4 Mérföldkövek

-   Az elkészült szoftver átadása

## 3. Üzleti folyamatok modellje

### 3.1 Üzleti szereplők
Vásárló
Megrendelő

### 3.2 Üzleti folyamatok

A vásárló megrendel egy vagy több terméket, a megrendelés bekerül a rendszerbe, majd pedig a termék kiszállításra kerül.

#### Üzleti folyamatok alkalmazott számára:

-   Vásárló regisztrálása a rendszerben: A customers táblába felvételre kerül a vásárló
-   Vásárló törlése a rendszerből: A customers táblából törlésre kerül a vásárló
    
-   Új termék regisztrálása: A products táblába felvételre kerül a termék
    
-   Termék törlése: A products táblába törlésre kerül a termék
    
-   Megrendelés - megrendelés felvétele: Az alkalmazott az orders táblába felveszi a megrendelést
    
-   Megrendelés - megrendelés visszavétele: Az alkalmazott az orders táblából kitörli a megrendelést
    
-   Lekérdezés: Az alkalmazott több lekérdezést is tudnak indítani. Például a megrendeléseket.
    

#### Üzleti folyamatok adminisztrátor jogosultsággal rendelkező alkalmazottak számára:

Az adminisztrátorok minden az alkalmazottaknál felsorolt funkciót elérnek, továbbá a következőket:

-   Alkalmazott regisztrálása: Az employees táblába felvételre kerül az alkalmazott
- 
-   Alkalmazott törlése: Az employees táblából törlésre kerül az alkalmazott

#### Üzleti folyamatok olvasók számára:

-   Listázás: A vásárló ki tudja listázni a saját rendeléseit
    

### 3.3 Üzleti entitások

-   termék
## 4. Követelmények

### 4.1 Funkcionális követelmények

-   A vásárló csak a saját megrendeléseihez férhet hozzá
    
  -   Az alkalmazott ki tud listázni, fel tud vinni, törölni tud termékeket
  
  - Adminisztrációs jogosultsággal rendelkező alkalmazott képes alkalmazottak felvételére és törlésére

### 4.2 Nemfunkcionális követelmények

-   Termék követelmények * Használhatósági követelmények A fejlesztésre kerülő szoftver a potenciális felhasználók számára könnyen tanulható és használható, minimális informatikai tudással alkalmazható. * Megbízhatósági követelmények A kialakítandó szisztéma webes felületen keresztül ér el adatbázist. Az adatbázis konkurens elérése biztosított, továbbá inkonzisztens állapotba történő kerülése normál használat mellett nem lehetséges. * Hatékonysági követelmények * Teljesítmény követelmények  
    
    -   Szervezeti követelmények
        
        -   Szállítási követelmények A szoftver a szerződésben meghatározott időpontig kerül átadásra, ellenkező esetben a szerződésben található kötbér illeti meg a megrendelőt.
        -   Implementációs követelmények A program a szerződésben meghatározott gépekre kerül telepítésre, a felhasználók számára teljesen használatra készen, az adatbázisba minimális mennyiségű adat is feltöltésre kerül a dolgozók gyakorlati képzésének megkönnyítése céljából.
        -   Szabványügyi követelmények A vonatkozó szabványügyi kritériumok Törvényi előírások, szabványok fejezetben találhatók.
    -   Külső követelmények
        
        -   Együttműködési követelmények A szoftverfejlesztő cég szakemberei és a megrendelő közötti kapcsolattartást a szerződésben meghatározott személyek végzik. Szükség esetén mindkét oldalról bevonnak további szakembereket az együttműködés optimalizálása céljából.
        -   Etikai követelmények A szoftverfejlesztő cég munkatársainak a jóváhagyott etikai kódex alapján kell viselkedniük.
        -   Jogi követelmények
            -   Titokvédelmi követelmények E téren a törvényi előírások az irányadók. A fejlesztő cég munkatársai vállalják, hogy a sem a könyvtár dolgozóinak, sem a felhasználók adatait harmadik fél számára nem adják át, továbbá a sikeres átadás átvételt követően a nem üzleti jellegű adatokat megsemmisítik.
            -   Biztonsági követelmények A szoftverfejlesztés során a felhasználók számára egylépcsős beléptetési rendszer lesz implementálva.

### 4.3 Törvényi előírások, szabványok

Szerzői jogi törvény (1999. évi LXXVI. törvény; röviden: Szjt.), illetve a 2016. évi XCIII. törvény A szerzői jogok és a szerzői joghoz kapcsolódó jogok közös kezeléséről

```
2018. évi LIV. törvény az üzleti titok védelméről

2015. évi CCXXII. törvény az elektronikus ügyintézés és a bizalmi szolgáltatások általános szabályairól

1995. évi LXVI. törvény a köziratokról, a közlevéltárakról és a magánlevéltári anyag védelméről

73/2018. (IV. 20.) Korm. rendelet a helyi közszolgáltatás információs rendszerről

187/2015. (VII. 13.) Korm. rendelet az elektronikus információs rendszerek biztonsági felügyeletét ellátó hatóságok,
valamint az információbiztonsági felügyelő feladat- és hatásköréről, továbbá a zárt célú elektronikus információs rendszerek
meghatározásáról

335/2005. (XII. 29.) Korm. rendelet a közfeladatot ellátó szervek iratkezelésének általános követelményeiről

Az ISO / IEC 12207 a nemzetközi szabvány, amely tartalmazza a szoftver életciklusának kiválasztási, bevezetési és ellenőrzési
módszerét.

Az ISO 9000 leírja a termékek előállításának hivatalosan szervezett folyamatát, valamint az előrehaladás irányításának és 
nyomon követésének módszereit.

Az ISO / IEC 24744 szoftverfejlesztés - Metamodel for Development Methodologies, egy Powertype-alapú metamodell
szoftverfejlesztési módszertanhoz.

ISO / IEC 15504 Információs technológia — folyamatértékelés, más néven a szoftverfolyamat-fejlesztési képesség meghatározása
(SPICE), "a szoftverfolyamatok értékelésének kerete".

```

## 5. Funkcionális terv

### 5.1 Rendszerszereplők

A rendszerünkben két rendszerszereplő csoportot különböztetünk meg. Az egyik a megrendeléseket tevő vásárlók csoportja. A másik a irodai adminisztrációt végző alkalmazottak csoportja. Az vásárlók igénybe veszik a rendszer szolgáltatásait, míg az alkalmazottak ezt kiszolgálják az olvasókat. Az alkalmazottak több jogosultsággal rendelkeznek, mint a vásárlók.  A alkalmazott csoport része az adminisztrátori jogosultsággal rendelkező alkalmazottak csoportja, akik teljes jogosultsággal rendelkeznek. Ők végzik a rendszerben az alkalmazottak adminisztrálását.

### 5.3 Határ osztályok

#### A vásárló felhasználói tevékenységeihez kapcsolódó határosztályok
 - orders

#### Az alkalmazott tevékenységeihez kapcsolódó határosztályok
 - orders
 -  customers
 - payments
 - orderdetails
 - products
 - productlines

#### Az alkalmazott adminisztrátor kiegészítő tevékenységeihez kapcsolódó határosztályok
 - employees
 - offices

## 6. Fizikai környezet

### 6.1 Vásárolt softwarekomponensek és külső rendszerek

Nincsenek vásárolt szoftverkomponensek.

### 6.2 Hardver és hálózati topológia

-   Az alkalmazás web platformra készül.
-   Internet böngészőn keresztül érhető el a felhasználó felület.
-   Szerverhez kapcsolódnak: -- kliens gépek a helyi hálózaton -- interneten bárki számára elérhető honlap

### 6.3 Fizikai alrendszerek

-   Webszerver: 8080-as porton elérhető HTTP szolgáltatás
-   Mysql adatbázis szerver
-   Kliens gépek: a követelményeknek megfelelő internet böngésző futtatására alkalmas PC-k.

### 6.4 Fejlesztő eszközök

-   IntelliJ IDEA

## 8. Architekturális terv

HTTP protokollon keresztül szolgálja ki webes felületen a rendszer felhasználóit. Az adatok tárolása a MySql adatbázis szerveren történik.

### 8.2 Az alkalmazás rétegei, fő komponensei, ezek kapcsolatai

A backend a webszereveren fut, a vékony kliens a böngészőből elérhető a felhasználók számára. Adatbázis szerver (Adatbázis) <-------> Webszerver (Üzleti logika) <-------> Kliens (Felhasználói felület)

### 8.3 Változások kezelése

Minden változás lekezelése szerver oldalon történik, a kliens oldalon nincs szükség új komponensek telepítésére.

## 9. Adatbázisterv

### Adatbázis séma
![classicmodels.png](https://github.com/sbazsi01/test/blob/main/classicmodels.png?raw=true)

### 9.3 Fizikai adatmodellt legeneráló SQL szkript

**A táblákat létrehozó parancsok**
CREATE TABLE `productlines` (
  `productLine` varchar(50) NOT NULL,
  `textDescription` varchar(4000) DEFAULT NULL,
  `htmlDescription` mediumtext DEFAULT NULL,
  `image` mediumblob DEFAULT NULL,
  PRIMARY KEY (`productLine`)
);

CREATE TABLE `offices` (
  `officeCode` varchar(10) NOT NULL,
  `city` varchar(50) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `addressLine1` varchar(50) NOT NULL,
  `addressLine2` varchar(50) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `country` varchar(50) NOT NULL,
  `postalCode` varchar(15) NOT NULL,
  `territory` varchar(10) NOT NULL,
  PRIMARY KEY (`officeCode`)
);

CREATE TABLE `employees` (
  `employeeNumber` int(11) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `firstName` varchar(50) NOT NULL,
  `extension` varchar(10) NOT NULL,
  `email` varchar(100) NOT NULL,
  `officeCode` varchar(10) NOT NULL,
  `reportsTo` int(11) DEFAULT NULL,
  `jobTitle` varchar(50) NOT NULL,
  PRIMARY KEY (`employeeNumber`),
  KEY `reportsTo` (`reportsTo`),
  KEY `officeCode` (`officeCode`),
  CONSTRAINT `employees_ibfk_1` FOREIGN KEY (`reportsTo`) REFERENCES `employees` (`employeeNumber`),
  CONSTRAINT `employees_ibfk_2` FOREIGN KEY (`officeCode`) REFERENCES `offices` (`officeCode`)
);

CREATE TABLE `products` (
  `productCode` varchar(15) NOT NULL,
  `productName` varchar(70) NOT NULL,
  `productLine` varchar(50) NOT NULL,
  `productScale` varchar(10) NOT NULL,
  `productVendor` varchar(50) NOT NULL,
  `productDescription` text NOT NULL,
  `quantityInStock` smallint(6) NOT NULL,
  `buyPrice` double NOT NULL,
  `MSRP` double NOT NULL,
  PRIMARY KEY (`productCode`),
  KEY `productLine` (`productLine`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`productLine`) REFERENCES `productlines` (`productLine`)
);

CREATE TABLE `customers` (
  `customerNumber` int(11) NOT NULL,
  `customerName` varchar(50) NOT NULL,
  `contactLastName` varchar(50) NOT NULL,
  `contactFirstName` varchar(50) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `addressLine1` varchar(50) NOT NULL,
  `addressLine2` varchar(50) DEFAULT NULL,
  `city` varchar(50) NOT NULL,
  `state` varchar(50) DEFAULT NULL,
  `postalCode` varchar(15) DEFAULT NULL,
  `country` varchar(50) NOT NULL,
  `salesRepEmployeeNumber` int(11) DEFAULT NULL,
  `creditLimit` double DEFAULT NULL,
  PRIMARY KEY (`customerNumber`),
  KEY `salesRepEmployeeNumber` (`salesRepEmployeeNumber`),
  CONSTRAINT `customers_ibfk_1` FOREIGN KEY (`salesRepEmployeeNumber`) REFERENCES `employees` (`employeeNumber`)
);

CREATE TABLE `payments` (
  `customerNumber` int(11) NOT NULL,
  `checkNumber` varchar(50) NOT NULL,
  `paymentDate` date NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY (`customerNumber`,`checkNumber`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`customerNumber`) REFERENCES `customers` (`customerNumber`)
);

CREATE TABLE `orders` (
  `orderNumber` int(11) NOT NULL,
  `orderDate` date NOT NULL,
  `requiredDate` date NOT NULL,
  `shippedDate` date DEFAULT NULL,
  `status` varchar(15) NOT NULL,
  `comments` text DEFAULT NULL,
  `customerNumber` int(11) NOT NULL,
  PRIMARY KEY (`orderNumber`),
  KEY `customerNumber` (`customerNumber`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customerNumber`) REFERENCES `customers` (`customerNumber`)
);

CREATE TABLE `orderdetails` (
  `orderNumber` int(11) NOT NULL,
  `productCode` varchar(15) NOT NULL,
  `quantityOrdered` int(11) NOT NULL,
  `priceEach` double NOT NULL,
  `orderLineNumber` smallint(6) NOT NULL,
  PRIMARY KEY (`orderNumber`,`productCode`),
  KEY `productCode` (`productCode`),
  CONSTRAINT `orderdetails_ibfk_1` FOREIGN KEY (`orderNumber`) REFERENCES `orders` (`orderNumber`),
  CONSTRAINT `orderdetails_ibfk_2` FOREIGN KEY (`productCode`) REFERENCES `products` (`productCode`)
);

## 10. Implementációs terv

A webszerveren futó java program tartalmazza az üzleti logikát. A felhasználói felület, ami böngészőben megjelenő weboldalak formájában érhető el Swagger felhasználásával készülnek.
MySql adatbázis szervert használunk az adatok tárolására.

### 10.1 Perzisztencia osztályok
 - CustomerEntity
 - EmployeeEntity
 - OfficeEntity
 - OrderDetailEntity
 - OrderEntity
 - PaymentEntity
 - ProductEntity
 - ProductLinesEntity

### 10.2 Üzleti logika osztályai
 - CustomerManagerImpl
 - EmployeeManagerImpl
 - OfficeManagerImpl
 - OrderDetailManagerImpl
 - OrderManagerImpl
 - PaymentManagerImpl
 - ProductLinesManagerImpl
 - ProductManagerImpl

## 11. Tesztterv

A rendszerterv szerint implementált szoftver tesztelésének célja, hogy ellenőrizze az _Üzleti folyamatok modellje_ című pontban meghatározott folyamatok helyes, specifikáció szerinti lefutását, valamint hogy a kliens webes felület felhasználóbarát módon jelenik meg, és használható különböző hardver és szoftverkörnyezetben.

A tesztelés során használt kiszolgáló hardverkonfigurációja a telepítés során használt hardverrel kompatibilis, teljesítményben (processzor, operatív memória, háttértár) nem tér el jelentősen. A telepítéshez természetesen az általunk ajánlott konfiguráció kerül beszerzésre a felhasználó könyvtár által.

A tesztelés során használt kliens hardverek a napjainkban általánosan elterjedt hardverkonfigurációjú PC-k illetve laptopok, melyeken a leggyakrabban használt böngészőkön (Google Chrome, Mozilla Firefox, Microsoft Edge) teszteljük a rendszert az alábbiakban részletezettek szerint. A minimum hardverkonfiguráció: Intel Celeron processzor, 4GB RAM, 128GB HDD, a képernyők felbontása: 1280x1024, 1920x1080.

A tesztelés során az üzleti folyamatokhoz tartozó különböző forgatókönyvek eredményét vizsgáljuk. Amennyiben az elvártnak megfelelő eredményt kapjuk, a teszteset sikeresnek tekinthető, ellenkező esetben a hibát rögzítjük a tesztjegyzőkönyvben. Ezt követően a feljegyzett hibákat javítjuk a szoftverben, és újbóli tesztelésnek vetjük alá a rendszert.

### 11.1 Tesztelt üzleti folyamatok adminisztrátor alkalmazottak számára:


**A) Alkalmazott regisztrálása:**  
| Mező                   | Követelmények                                                                                                                         |
|------------------------------|---------------------------------------------------------------------------------------------------------------------------------------|
| employeeNumber              | 1. Kötelező 2. Maximum 11 karakter hosszú legyen, csak számok szerepelhetnek benne |                                             |
| lastName                  | 1. Kötelező 2. Nem tartalmazhat számjegyet, nagybetűvel kezdődik, több névtagból is állhat                                            |
| firstName                       | 1. Kötelező 2. Nem tartalmazhat számjegyet, nagybetűvel kezdődik, több névtagból is állhat                                            |                                     |
| extension            | 1. Kötelező 2. Maximum 10 karakter hosszú legyen                                          |
| officeCode               | 1. Kötelező 2. Maximum 10 karakter hosszú legyen, csak számok szerepelhetnek benne                                                                      |
| reportsTo              | 1. Opcionális 2. Maximum 11 karakter hosszú legyen, csak számok szerepelhetnek benne                                                                     |
| jobTitle | 1. Kötelező 2. Maximum 50 karakter hosszú legyen                                            
| Telefonszám                  | 1. Kötelező 2. Kötött formátumú: 11 számjegy                                                                                          |
| email                   | 1. Kötelező 2. Kötött formátumú: fióknév@domainnév                                                 

**Tesztesetek:**

1.  Bevitt adatok helyesek, megfelelnek a követelményeknek.  
    Elvárt eredmény:  
    a) Az alkalmazott táblában megjelenik az alkalmazott a megadott adatokkal.   
2.  Bevitt adatok között szerepelnek a fenti követelményeknek nem megfelelő adatok.  
    Elvárt eredmény: a rendszer hibaüzenetben jelzi a felhasználó számára a hibákat, az adatbázisban nem jelenik meg új rekord az employees táblában. Ellenőrzés az 1. pont szerint.

**B) Alkalmazott adatainak módosítása:**  

**Tesztesetek:**

1.  Adatmódosítás helyes adatokkal. Elvárt eredmény: a megfelelő rekord módosul az employees táblában. 
2.  Adatmódosítás követelményeknek nem megfelelő adatokkal. Elvárt eredmény: a rendszer hibaüzenetben jelzi a felhasználó számára a hibát, az adatbázisban nem módosul rekord az employees táblában. 

**C) Alkalmazott törlése:**  

**Tesztesetek:**
1.  Létező azonosítójú alkalmazott törlése. Elvárt eredmény: a megfelelő rekord törlődik az employees táblából az adatbázisban. 

### 11.2 Tesztelt üzleti folyamatok könyvtárosok számára:

**A) Rendelés adatainak módosítása:**  

**Tesztesetek:**

1.  Adatmódosítás helyes adatokkal. Elvárt eredmény: a megfelelő rekord módosul az orders táblában. 
2.  Adatmódosítás követelményeknek nem megfelelő adatokkal. Elvárt eredmény: a rendszer hibaüzenetben jelzi a felhasználó számára a hibát, az adatbázisban nem módosul rekord az orders táblában. 

**B) Rendelés törlése:**  

**Tesztesetek:**
1.  Létező rendelés törlése. Elvárt eredmény: a megfelelő rekord törlődik az orders táblából az adatbázisban. 

### 11.2.1 Vásárlók adminisztrációjának tesztelése:


**A) Saját rendelések megtekintése:**  

**Tesztesetek:**

1.  Létezik ilyen vásárló: Az orders táblából visszaadja a vásárló rendeléseit.
2. Nem létezik ilyen vásárló: Nem ad vissza semmit az orders táblából.

## 12. Telepítési terv

Fizikai telepítési terv: -Szükség van egy adatbázis szerverre, amely közvetlenül csatlakozik a webszerverhez. -A webszerverre közvetlenül az internetről kapcsolódnak rá a kliensek.

Szoftver telepítési terv: -A szoftver webes felületéhez csak egy ajánlott a böngésző telepítése szükséges ( Google Chrome, Firefox, Opera, Edge) külön szoftver nem kell hozzá.
