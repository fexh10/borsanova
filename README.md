# Bo{r,s}sa nova

Questo repository contiene il **progetto d\'esame** dell\'insegnamento
di \"Programmazione II\" all\'a.a 2024/2025.

Obiettivo del progetto è modellare il comportamento di alcuni
**operatori** che scambiano **azioni** di alcune **aziende** quotate in
varie **borse**.

Per portare a termine il lavoro dovrà decidere se e quali classi
(concrete o astratte) e quali interfacce implementare. Per ciascuna di
esse **dovrà descrivere** (in formato Javadoc attraverso commenti
presenti nel codice) le scelte relative alla **rappresentazione** dello
stato (con particolare riferimento all\'**invariante di
rappresentazione** e alla **funzione di astrazione** così come definiti
nel libro di testo dell\'insegnamento e illustrati a lezione) e ai
**metodi** (con particolare riferimento a *pre/post-condizioni* ed
*effetti collaterali*, soffermandosi ad illustrare le ragioni della
*correttezza* solo per le implementazioni che riterrà più critiche).
Osservi che l\'esito di questa prova, che le consentirà di accedere o
meno all\'orale, si baserà tanto su questa documentazione quanto sul
codice sorgente.

Può prendere visione dei dettagli tecnici riguardanti la realizzazione
del progetto nelle apposite [istruzioni](ISTRUZIONI.md). Osservi che
**la presenza di errori o fallimenti nella compilazione, generazione
della documentazione ed esecuzione dei test impedisce il superamento
dell\'esame**.

## Descrizione delle entità coinvolte

Le entità coinvolte nel progetto sono: **aziende**, **operatori**,
**borse** e **azioni**. Di seguito ne vengono illustrate le
caratteristiche principali (le informazioni che le descrivono e le
competenze che possiedono).

### Azienda

Una azienda è caratterizzata da un *nome* (non vuoto); ogni azienda può
decidere di *quotarsi* in una o più delle borse presenti (al più una
volta per ciascuna di esse), nel farlo decide per ciascuna borsa sia il
*numero* di azioni che intende emettere che il loro *prezzo* unitario
(entrambe le grandezze sono per semplicità rappresentate da numeri
interi positivi). Ogni azienda tiene traccia delle borse in cui è
quotata.

### Operatore

Un operatore (di borsa) è caratterizzato da un *nome* (non vuoto) e da
un *budget* (rappresentato per semplicità da un numero intero non
negativo, inizialmente pari a 0). Ogni operatore può decidere di
*acquistare* o *vendere* azioni di una o più aziende presenti in una o
più borse. L\'acquisto di azioni comporta una diminuzione del budget
pari al prezzo corrente dell\'azione per il numero di azioni acquistate
(che deve essere un numero intero positivo), mentre la vendita di azioni
comporta un aumento del budget pari al prezzo corrente dell\'azione per
il numero di azioni vendute (che deve essere un numero intero positivo).
Il budget può essere aumentato grazie a un *deposito* e diminuito grazie
a un *prelievo* (entrambi rappresentati da numeri interi positivi), ma
in nessun caso (neppure per l\'acquisto di azioni) può diventare
negativo. Il *capitale totale* di un operatore è dato dalla somma del
budget e del valore delle azioni possedute. Ogni operatore tiene traccia
del proprio budget e delle azioni possedute correntemente (ma non
necessariamente dell\'intera storia degli acquisti e delle vendite); è
responsabilità esclusiva dell\'operatore garantire che il suo budget non
diventi mai negativo.

### Borsa e azione

Una borsa è caratterizzata da un *nome* (non vuoto); la borsa tiene
traccia delle quotazioni delle varie aziende ed emette le relative
**azioni**. Inoltre, tiene traccia delle **allocazioni** di tali azioni
agli operatori, in modo da poter sapere per ciascuna azienda quotata di
quante azioni è in possesso ciascun operatore e quante non siano ancora
state vendute; è responsabilità esclusiva della borsa garantire che un
operatore non venda più azioni di quante gliene siano correntemente
allocate, o compri più azioni di quante siano disponibili al momento
dell\'acquisto.

La borsa può cambiare il prezzo delle azioni in seguito ad ogni vendita,
o acquisto; per farlo segue una **politica di prezzo** che, data
l\'azione e il numero di quante ne sono state vendute, o comprate,
indica il nuovo prezzo dell\'azione; la politica di prezzo può cambiare
nel tempo. Ad esempio, una politica di prezzo

-   ad *incremento costante* $k$ è tale per cui ad
    ogni acquisto, il prezzo dell\'azione aumenta di
    $k$ unità, restando invariato in caso di vendita;

-   a *decremento costante* $h$ è tale per cui ad
    ogni vendita il prezzo dell\'azione diminuisce di
    $h$ unità (se maggiore dI $h$,
    o diventando $1$ viceversa), restando invariato
    in caso di acquisto;

-   a *variazione costante* $(k, h)$ che si comporta
    come un incremento costante $k$ (in caso di
    acquisto) e come un decremento costante $h$ (in
    caso di vendita);

-   altre politiche possono cambiare il prezzo a seconda del numero di
    azioni scambiate, ad esempio aumentando in modo lineare il prezzo
    tanto più quante azioni sono vendute, oppure diminuendo il prezzo in
    modo esponenziale.

Ovviamente l\'azione dipende dalla borsa che l\'ha emessa e *il suo
prezzo può essere cambiato esclusivamente da essa*; qualunque altra
entità oltre alla borsa che l\'ha emessa venga a conoscenza di una
azione potrà interrogarla per conoscerne: l\'azienda e borsa cui
corrisponde, il suo prezzo corrente, quante ne sono state emesse e
quante sono correntemente disponibili, ma non potrà modificare
direttamente nessuno di tali valori!

## Un suggerimento su identità e confronti

Le entità sopra descritte hanno tutte un *nome*, può essere molto utile
ritenere identiche due entità che hanno il medesimo nome (per
semplificare i confronti tra esse, e per mantenerle in ordine
lessicografico); questo sarebbe però difficilmente sensato se fosse
possibile, per ciascun tipo, creare più entità col medesimo nome.

Per fare in modo che per ciascuna entità non ce ne siano di omonime, è
possibile sostituire i costruttori pubblici con metodi di fabbricazione
che, grazie ad un attributo statico che tenga traccia di tutti gli
oggetti istanziati, verifichino che non ci siano omonimie (e consentano,
al contempo, di reperire le istanze a partire dal nome).

```java
public class NameBasedExample implements Comparable<NameBasedExample> {

  private static final Map<String, NameBasedExample> INSTANCES = new TreeMap<>();

  public final String name;

  //  other fields may appear here

  public static NameBasedExample of(final String name) {
    if (Objects.requireNonNull(name, "Name must not be null.").isBlank())
      throw new IllegalArgumentException("Name must not be empty.");
    if (!INSTANCES.containsKey(name)) INSTANCES.put(name, new NameBasedExample(name));
    return INSTANCES.get(name);
  }

  private NameBasedExample(final String name) {
    this.name = name;
    // other initializations may appear here
  }

  // other methods may appear here

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof NameBasedExample other)) return false;
    return name.equals(other.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public int compareTo(NameBasedExample other) {
    return name.compareTo(other.name);
  }
}
```

Ovviamente sono possibili molte altre strategie per gestire l\'omonimia
così come è possibile confrontare le entità con criteri più ricchi di
quello basato sul solo nome. Attenzione però! Sarebbe difficilmente
plausibile usare il codice sopra per definire una gerarchia di cui le
entità del progetto siano tutte sottoclassi: non solo sarebbe più
difficile controllare che l\'unicità del nome valga solo tra istanze
della stessa classe, ma sopratutto diventerebbe pressoché inevitabile
consentire la comparazione di istanze di entità di sottoclassi diverse
tra loro.

> **Nota bene**: può accadere che se più test vengono eseguiti nella
> medesima istanza della JVM l\'unicità del nome non sia garantita, in
> quanto le istanze create in un test potrebbero persistere e
> interferire con quelle create in un altro test. Per questa ragione i
> test del docente non contengono mai lo stesso nome in più di un test.

## Cosa è necessario implementare

Dovrà implementare una gerarchia di oggetti utili a:

-   rappresentare le entità fondamentali **aziende**, **operatori**,
    **borse** e **azioni** e, tra le altre, le informazioni accessorie
    come le **allocazioni** e le **politiche di prezzo**;

-   gestire (oltre alla costruzione delle entità) le competenze
    essenziali come la *quotazione* in borsa e le *compravendite* di
    azioni senza dimenticare le altre competenze che rendano *adeguate*
    (nel preciso senso dato a questo termine nel contesto
    dell\'insegnamento) le specifiche dei vari oggetti (con particolare
    riferimento all\'osservabilità);

-   implementare tutti i *client*, ossia le classi di test (come
    descritto di seguito).

Al fine di evitare confusione con i client (descritti nella prossima
sottosezione), è consigliabile che il suo codice sia contenuto in una
gerarchia di pacchetti, ad esempio nel pacchetto `borsanova`, i cui
sorgenti dovranno essere nella directory `src/main/java/borsanova`.

### Le classi client

Secondo quanto illustrato nelle [istruzioni](ISTRUZIONI.md), per
guidarla nel processo di sviluppo, in `src/main/java/clients/` le sono
forniti alcuni scheletri di test sotto forma di \"client\" di cui **lei
dovrà produrre una implementazione** (secondo le specifiche che troverà
nella documentazione contenuta nel sorgente di ciascuno di essi)
*facendo uso delle classi della sua soluzione*; per ciascun client,
nella sottodirectory di `tests/clients/` avente nome uguale a quello
della classe client, si trovano un corredo di file di input e output che
le permetteranno di verificare il corretto funzionamento del suo codice.

Faccia attenzione al fatto che i client hanno non a caso questo nome: il
loro compito è usare le classi da lei implementate per svolgere il loro
compito, dovrebbero contenere poco e semplice codice al loro interno,
praticamente solo quello strettamente necessario a leggere i dati di
input, istanziare ed esercitare le classi da lei implementate e quindi
per scrivere i dati di output ottenuti tramite di esse.

Si ricorda che **il progetto non sarà valutato a meno che tutti i test**
svolti tramite il comando `gradle test` **diano esito positivo** .

## Codice di condotta

Dovendo svolgere il progetto a casa non le vengono imposte particolari
restrizioni delle quali sarebbe peraltro difficile verificare il
rispetto.

Le è pertanto **consentito** di avvalersi:

-   di qualunque risorsa disponibile in rete,
-   di strumenti di supporto basati sull\'AI (come [GitHub
    Copilot](https://github.com/features/copilot), o
    [ChatGPT](https://chat.openai.com/)),
-   del *confronto* con altri studenti, o professionisti,

sia per la *progettazione* che per l\'*implementazione* e
*documentazione* del codice. Ogni supporto che la aiuti a apprendere e
dominare gli obiettivi culturali dell\'insegnamento è benvenuto!

D\'altro canto le viene **formalmente richiesto di elencare** (nella
documentazione del codice) in modo chiaro ed esaustivo **ogni risorsa di
cui si è avvalso al di fuori di quelle esplicitamente indicate come
materiale didattico** dell\'insegnamento. L\'omissione di tale elenco
può costituire motivo di **respingimento** del progetto e, in gravi casi
di *plagio* alle **sanzioni disciplinari** previste.

Si sottolinea che consegnando il progetto lei dichiara di fatto di
esserne l\'**unico autore**, assumendosi la piena responsabilità
dell\'**originalità** del codice e della documentazione che esso
include, nonché della completezza e veridicità del suddetto elenco. Per
questa ragione **non le è consentito condividere il suo codice con altri
studenti**.

Durante la discussione orale, eventuali incertezze nell\'*illustrare*,
*giustificare* o *modificare* il materiale consegnato non potranno che
essere a lei esclusivamente addebitate e, come tali, **valutate
negativamente**.

> **Nota bene**: la violazione del presente codice di condotta, qualora
> ne venga accertato il dolo, può condurre a **sanzioni disciplinari**
> come previste dal comma d) dell\'art. 52 del [Regolamento generale
> d\'Ateneo](https://www.unimi.it/sites/default/files/regolamenti/Regolamento%20generale%20d%27Ateneo_in%20vigore%20dal%202%20giugno%202020.pdf)

## Note legali e copyright

Ai sensi della Legge n. 633/1941 e successive modificazioni, l\'autore
si riserva, in ogni forma e modo nei limiti fissati dalla legge, il
diritto esclusivo di pubblicare e di utilizzare il materiale contenuto
nel presente repository.

Più specificatamente, è fatto **divieto di riprodurre, trascrivere,
comunicare al pubblico, distribuire, tradurre, elaborare e modificare il
presente materiale** (codice sorgente compreso), in tutto o in parte,
senza specifica autorizzazione scritta dell\'autore.
