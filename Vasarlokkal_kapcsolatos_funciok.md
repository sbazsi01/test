

# Vásárlókkal kapcsolatos funkciók teszt jegyzőkönyv

## Tesztelést végezte: Sulyok Balázs

## Operációs rendszer: WIN10

## Böngésző: Mozilla Firefox 84.0.2

## Dátum: 2021.01.17.

## Talált hibák száma: 0

# Vásárlókkal kapcsolatos funkciók tesztelése

- Minden alkalmazottnak van hozzáférése a vásárlók adataihoz.
- A vásárlók adatai a customers táblában vannak eltárolva.

### Vásárlók kiolvasása
-   Nem létező vásárló azonosítót megadva, nem ad vissza vásárlót.
-   Megfelelő vásárló azonosító megadása után megjelennek a vásárló adatai.
-  Ha nincs megadva vásárló azonosító, akkor az összes vásárló adatai megjelennek.

### Vásárlók hozzáadása
- Már létező vásárló azonosító esetén  nem kerül hozzáadásra a vásárló.
- Ha a vásárló azonosító nem csak számokat tartalmaz, akkor nem kerül hozzáadásra a vásárló. 
- Ha az egyik adat túl hosszú, nem kerül hozzáadásra a vásárló.
- Ha nincs kitöltve a customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, city, country mező, akkor nem kerül hozzáadásra a vásárló.
- Megfelelő adatokat megadva, hozzáadásra kerül a vásárló.

### Vásárlók módosítása

- Nem létező vásárló azonosító esetén, nem lesz módosítás.
- Ha az egyik adat túl hosszú, akkor sikertelen a módosítás.
- Ha nincs kitöltve a customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, city, country mező, akkor nem kerül módosításra a vásárló.
-   Megfelelő adatokat megadva, módosításra kerül a vásárló.

### Vásárlók törlése

- Ha nincs megadva vásárló azonosító, nem lesz törlés.
- Ha nem létező vásárló azonosító van megadva, nem lesz törlés.
- Létező vásárló azonosító esetén, törlésre kerül az a vásárló, akinek az azonosítóját megadtuk.

