
# Alkalmazottakkal kapcsolatos funkciók teszt jegyzőkönyv

## Tesztelést végezte: Sulyok Balázs

## Operációs rendszer: WIN10

## Böngésző: Mozilla Firefox 84.0.2

## Dátum: 2021.01.17.

## Talált hibák száma: 0

# Alkalmazottakkal kapcsolatos funkciók tesztelése

-   Csak az éri el, aki adminisztrációs jogokkal rendelkezik
- Az alkalmazottak adatai az employees táblában vannak eltárolva.

### Alkalmazottak kiolvasása
-   Nem létező alkalmazott azonosítót megadva, nem ad vissza alkalmazottat.
-   Megfelelő alkalmazott azonosító megadása után megjelennek az alkalmazott adatai.
-  Ha nincs megadva alkalmazott azonosító, akkor az összes alkalmazott adatai megjelennek.

### Alkalmazott hozzáadása
- Már létező alkalmazott azonosító esetén, akkor nem kerül hozzáadásra az alkalmazott.
- Ha az alkalmazott azonosító számokat tartalmaz, akkor nem kerül hozzáadásra az alkalmazott. 
- Ha az egyik adat túl hosszú, nem kerül hozzáadásra az alkalmazott.
- Nem létező iroda esetén, nem kerül hozzáadásra az alkalmazott.
- Ha a reports to kivételével nincs kitöltve egy mező, akkor nem kerül hozzáadásra az alkalmazott.
- Ha a reports to mezőben nem létező alkalmazott azonosító van megadva, akkor hozzáadásra kerül az alkalmazott. 
- Megfelelő adatokat megadva, hozzáadásra kerül az alkalmazott.

### Alkalmazott módosítása

- Nem létező alkalmazott azonosító esetén, nem lesz módosítás.
- Ha az egyik adat túl hosszú, akkor sikertelen a módosítás.
- Nem létező iroda esetén, nem kerül módosításra az alkalmazott.
- Ha a reports to kivételével nincs kitöltve egy mező, akkor nem kerül módosításra az alkalmazott.
- Ha a reports to mezőben nem létező alkalmazott azonosító van megadva, akkor módosításra kerül az alkalmazott.
-   Megfelelő adatokat megadva, módosításra kerül az alkalmazott.

### Alkalmazottak törlése

- Ha nincs megadva alkalmazott azonosító, nem lesz törlés.
- Ha nem létező alkalmazott azonosító van megadva, nem lesz törlés.
- Létező alkalmazott azonosító esetén, törlésre kerül az az alkalmazott, akinek az azonosítóját megadtuk.

