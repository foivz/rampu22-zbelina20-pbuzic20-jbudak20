# Lost & Found App

## Projektni tim

Ime i prezime | E-mail adresa (FOI) | JMBAG | Github korisničko ime | Seminarska grupa
------------  | ------------------- | ----- | --------------------- | ----------------
Zvonimir Belina | zbelina20@student.foi.hr | 0016149673 | zbelina20 | G01
Josip Budak | jbudak20@student.foi.hr | 0016147588 | jbudak20-foi | G01
Patrik Bužić | pbuzic20@student.foi.hr | 0016146757 | pbuzic20 | G01

## Opis domene
Primjetili smo da je proteklih godina broj izgubljenih stvari na području studentskog doma Varaždin izrazito velik. Studenti su za izgubljenu imovinu kreirali objave na Facebook-u, unutar grupe za studentski dom. Objave unutar Facebook grupa su nepregledne, oskudne te su se brzo izgubile među ostalim objavama. Želimo kreirati rješenje ovog problema pod nazivom Lost & Found App (L&F). Lost & Found App (L&F) je aplikacija koja će omogućiti studentima da lakše pronađu svoju izgubljenu imovinu ili pomognu drugima da pronađu izgubljenu imovinu. Aplikacija je prvotno osmišljena za stanare Studentskog doma Varaždin, no u budućnosti bi se mogla proširiti na ostale studentske domove ili veća područja.      

## Specifikacija projekta
Kako bi korisnik mogao koristiti aplikaciju, mora napraviti svoj korisnički račun. Nakon što se korisnik registrirao i prijavio u aplikaciju, na zaslonu mu se prikazuju objave od drugih korisnika. Zaslon ima dva dijela: "Izgubio sam" i "Pronašao sam" koje će moći filtrirati i pretraživati. Nakon otvaranja objave korisnik će ju moći komentirati i kontaktirati objavitelja te objave. Ako se korisnik odluči kreirati svoju objavu, nju će moći izmjenjivati i obrisati.

Oznaka | Naziv | Kratki opis | Odgovorni član tima
------ | ----- | ----------- | -------------------
F01 | Login i registracija | Za pristup aplikaciji korisnik mora imati vlastiti korisnički račun | Josip Budak
F02 | Pregledati objave | Korisnik će imati uvid u sve objave, odnosno izgubljene stvari.  | Zvonimir Belina
F03 | Kreirati objave | Korisnik će moći kreirati objavu vezanu uz pronalazak ili gubitak imovine. | Patrik Bužić
F04 | Brisati i ažurirati objave | Korisnik će moći brisati te ažurirati objavu vezanu uz pronalazak ili gubitak imovine. | Josip Budak
F05 | Komentirati objave | Korisnik će imati mogućnost komentiranja objave. | Patrik Bužić
F06 | Notificirati korisnika | Korisnik će dobivati obavijesti (komentar, promjena stanja objave...). | Patrik Bužić
F07 | Filtrirati i pretraživati objave | Korisnik će moći flitrirati sve objave po vrsti imovine. Korisnik će moći pretraživati proizvode po imenima. | Zvonimir Belina
F08 | Kontaktirati osobe koja su kreirale objave putem poziva. | Korisnik će moći stupiti u kontakt s osobom koja je kreirala objavu preko poziva.| Zvonimir Belina
F09 | Izraditi grafička izvješća  | Aplikacija će izrađivati grafička izvješća o ukupnim mjesečnim i izgubljenim i pronađenim predmetima. | Josip Budak
## Tehnologije i oprema
Za izradu same aplikacije koristit ćemo 'Android Studio' unutar kojeg je emulator za razne uređaje, pomoću kojeg ćemo testirati aplikaciju. Verzioniranje programskog koda će se provoditi preko git-a i Github-a, a za pohranu podataka korisitmo MySQL bazu podataka. Smatramo da nam neće biti potrebna neka dodatna tehnologija.


## Baza podataka i web server
"Trebamo bazu podataka i pristup serveru za PHP skripte"

## .gitignore
Uzmite u obzir da je u mapi Software .gitignore konfiguriran za nekoliko tehnologija, ali samo ako će projekti biti smješteni direktno u mapu Software ali ne i u neku pod mapu. Nakon odabira konačne tehnologije i projekta obavezno dopunite/premjestite gitignore kako bi vaš projekt zadovoljavao kriterije koji su opisani u ReadMe.md dokumentu dostupnom u mapi Software.
