---
title: ISTRUZIONI
---

# Istruzioni d\'uso

Al fine di favorire l\'automazione di alcuni compiti, tra i quali la
**compilazione**, la **generazione della documentazione** e
l\'**esecuzione dei test** *black-box*, questo progetto fa uso di un
*build automation tool* denominato [Gradle](https://gradle.org/)
unitamente al *testing framework*
[Jubbiot](https://github.com/prog2-unimi/jubbiot) (a sua volta basato su
[JUnit](https://junit.org/junit5/)).

Sebbene tali strumenti siano stati utilizzati durante le lezioni ed
esercitazioni, ragione per cui non dovrebbero risultare del tutto
sconosciuti, una *conoscenza approfondita del funzionamento di essi non
è affatto necessaria per il superamento dell\'esame*, perché sono
predisposti e completamente configurati dal docente, come illustrato
nelle sezioni seguenti.

L\'unica cosa che lei dovrà fare è **installare il *Java Developer Kit*
(JDK)** in una versione sufficientemente recente, la configurazione
fornita col progetto provvederà poi ad installare automaticamente sia la
versione 21 del JDK che il tool Gradle e le librerie Jubbiot e JUnit.

## Organizzazione del materiale

Il contenuto della directory dove si trova questo file segue
l\'organizzazione dei file prevista dal *build automation tool* scelto
per la gestione del codice.

In particolare, i **file sorgenti** sono contenuti nella directory
`src/main/java`, divisi in sottodirectory a seconda della strutturazione
in *package*. Può sviluppare le sue classi (e interfacce) nella
gerarchia di pacchetti che riterrà più opportuna (sotto tale directory).
Man mano che realizzerà il suo codice dovrà completare il codice delle
classi nel pacchetto `clients` (che si trovano nella directory
`src/main/java/clients`) in modo da poter eseguire i *test* (come
illustrato di seguito).

Al termine, come illustrato nell\'apposita sezione, gli unici file che
verranno consegnati al docente saranno quelli contenuti nella directory
`src/main/java` (e eventuali sottodirectory); è comunque fortemente
sconsigliato che lei modifichi, o rimuova, il resto del contenuto di
questa directory.

## Come compilare ed eseguire il codice

Può compilare il codice con il comando:

```
./gradlew assemble
```

se usa una ragionevole versione di GNU/Linux, oppure se usa Windows (qui
e di seguito) può sostituire `./gradlew` con `gradlew.bat`. Presti
attenzione ai problemi di compilazione riportati da questo comando che
vanno eliminati prima di procedere con i passi descritti nelle prossime
sezioni.

> **Nota bene**: la presenza di errori o warning in questa fase
> **impedisce il superamento dell\'esame**.

La compilazione genererà i file `.class` nella directory
`build/classes`. Può eseguire il codice di una specifica classe, ad
esempio `my.package.HelloWorld` con il comando

```
./gradlew runClass -PmainClass=my.package.HelloWorld
```

## Come generare la documentazione

Può generare la documentazione con il comando:

```
./gradlew javadoc
```

Tale comando è configurato per riportare un errore anche in caso di
*warning*, al fine di aiutarla nel comprendere se la documentazione è,
almeno dal punto di vista sintattico, completa.

> **Nota bene**: la presenza di errori o warning in questa fase
> **impedisce il superamento dell\'esame**.

Può trovare la documentazione generata nella directory
`build/docs/javadoc`, prendendone visione aprendo il file
`build/docs/javadoc/index.html` con qualunque browser ragionevolmente
recente.

## Come eseguire i test

Può eseguire i test con il comando:

```
./gradlew test
```

Tale comando eseguirà tutte le classi *client* presenti nella directory
`src/clients` facendo uso dei file predisposti dal docente (che non deve
in alcun modo modificare) nella sottodirectory di `tests/clients` il cui
nome coincide con il nome del *client*.

Tali directory contengono una serie di file di nome `input-N.txt`,
`args-N.txt` e `expected-N.txt` (al variare di `N` da `1` in poi) che
costituiscono rispettivamente l\'*input* e gli *argomenti sulla lina di
comando* che il sistema di test darà in pasto al `main` del *client* e
l\'output *atteso*. Se il suo output differisce da quello atteso verrà
riportato un **fallimento**.

> **Nota bene**: la presenza anche si un solo fallimento **impedisce il
> superamento dell\'esame**.

Ci sono molti modi per prendere visione delle eventuali differenze che
hanno condotto al fallimento:

-   può aprire il file `build/reports/tests/test/index.html` con
    qualunque browser ragionevolmente recente,

-   se usa [Visual Studio Code](https://code.visualstudio.com/), può
    usare il [Test Runner for
    Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-test)
    (grazie al `build.gradle` l\'editor verrà automaticamente
    configurato per usare JUnit 5 e Jubbiot);

-   se definisce la variabile d\'ambiente `GENERATE_ACTUAL_FILES` (ad
    esempio con il comando di shell `export GENERATE_ACTUAL_FILES=1` se
    usa GNU/Linux con una shell Posix) prima di eseguire i test, nelle
    varie directory coi file predisposti dal docente verranno generati i
    file `actual-N.txt` contenenti l\'output prodotto dal suo codice
    (l\'eventuale differenza tra il contenuto dei file `expected-N.txt`
    e `actual-N.txt` costituisce ragione di fallimento).

Per maggiori informazioni sul funzionamento dei test consulti
l\'[esempio d\'uso di
Jubbiot](https://github.com/prog2-unimi/jubbiot/blob/master/README.md#example);
in particolare presti attenzione alla sezione sulla [generazione degli
output dello
studente](https://github.com/prog2-unimi/jubbiot/blob/master/README.md#generating-actual-outputs).

## Come consegnare la soluzione

Al termine del lavoro può preparare l\'archivio da consegnare al docente
con il comando:

```
./gradlew consegna
```

questo comando creerà il file `consegna.zip` nella directory base del
progetto, che dovrà provvedere a caricare **entro il mezzogiorno della
data dell\'appello** sul sito [Upload@DI](https://upload.di.unimi.it/)
nell\'apposita sessione di consegna denominata \"Programmazione II
(Informatica) (Consegna progetto --- Santini)\".

> **Nota bene**: verrà preso in considerazione solo l\'**ultimo**
> archivio caricato *entro la scadenza prescritta* e di *formato
> corretto* (come ottenuto tramite il comando `consegna`) relativo a una
> *regolare iscrizione* (tramite il SIFA/UniMIA) all\'appello in corso.

## Aggiornamento del materiale e QA col docente

Qualora abbia delle **domande sulla traccia**, può usare un client
[Matrix](https://matrix.org/) per consultare lo spazio
[Prog2@Unimi-ExamQA](https://matrix.to/#/#prog2@unimi-examqa:gitter.im);
osservi che *non è garantita alcuna tempestività nella risposta*: se ha
urgenza, [prenoti un **ricevimento
studenti**](https://cal.com/mapio/ricevimento).

Sebbene sia raro, **può capitare che il docente aggiorni il materiale
del progetto** (ad esempio, a seguito di segnalazioni di errore da parte
degli studenti).

Questa è la versione 23a4860 del progetto (del: 2025-01-09, ora:
23:17:34), contenuta nel file \`progetto-23a4860.zip\' che ha scaricato
dal sito [Upload@DI](https://upload.di.unimi.it/).

Controlli periodicamente il sito per verificare se è stata rilasciata
una nuova versione (il cui file zip avrà un nome diverso da quello
attuale). Nel caso ciò avvenisse:

-   faccia una *copia di sicurezza* del suo lavoro (nel caso commetta
    errori nel seguire i prossimi passi)!
-   scarichi il nuovo file `.zip` da
    [Upload@DI](https://upload.di.unimi.it/),
-   lo estragga in una *nuova directory*,
-   copi i file della sua soluzione dalla directory `src/main/java`
    della sua vecchia soluzione nella nuova directory, avendo cura di
    **verificare che i client che ha già sviluppato soddisfino le nuove
    specifiche** (se presenti),
-   proceda con lo sviluppo (e quindi la consegna) nella nuova
    directory.

### Approcci alternativi

Chi non intendesse usare il *build automation tool* può comunque
compilare ed eseguire il codice e i test, nonché generare la
documentazione, usando il JDK tramite la linea di comando, oppure un IDE
a sua scelta.

> **Nota bene**: non sarà valutato alcun codice che presenti *errori* (o
> *warning*) di compilazione (sia nella parte di *Java* che di
> *Javadoc*).
>
> Per questa ragione si consiglia, qualora si decida di usare gli usuali
> comandi del JDK, di aggiungere sempre le opzioni `-Xlint:all -Werror`
> al comando `javac` e `-Xdoclint:all -Werror` al comando `javadoc` (sia
> da riga di comando che attraverso l\'IDE scelto).
>
> Similmente, non sarà valutato alcun codice che presenti *discrepanze*
> nel comportamento dei *client* rispetto a quanto atteso.

Il docente **non fornirà alcun supporto per l\'uso di approcci
differenti** da quello basato sul *build automation tool*. suggerito e
preconfigurato.
