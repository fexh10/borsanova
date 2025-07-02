package borsanova;

import borsanova.Borsa.Azione;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Classe che rappresenta un Operatore.
 * 
 * <p> Un Operatore è caratterizzato da:
 *  <ul>
 *   <li>un nome(unico e non modificabile);</li>
 *   <li>un budget;</li>
 *   <li>un insieme di azioni possedute.</li>
 * </ul>
 * 
 * <p> Dato che il nome di un Operatore è unico,
 * non è possibile creare due operatori con lo stesso nome.
 * 
 * <p>Un Operatore è partecipante del mercato azionario e può:
 *  <ul>
 *  <li>depositare denaro nel proprio budget;</li>
 *  <li>prelevare denaro dal proprio budget;</li>
 *  <li>acquistare azioni di un'azienda da una borsa;</li>
 *  <li>vendere azioni di un'azienda in una borsa;</li>
 *  <li>calcolare il valore delle azioni possedute;</li>
 *  <li>calcolare il proprio capitale totale.</li>
 * </ul>
 * 
 * <p>Il criterio di confronto e di ordinamento tra gli operatori è il nome.</p>
 * 
 * <p> Strumenti di supporto utilizzati in questa classe:
 *  <ul>
 *  <li> GitHub Copilot (GPT 4o), per il refactoring del metodo {@link #toString()}
 *  e una scrittura del codice più fluida.</li>
 *  </ul>
 * 
 * @see Comparable
 * 
 */
public class Operatore implements Comparable<Operatore> {

    /** Registro delle istanze uniche di Operatore.
     * La chiave è il nome dell'Operatore, il valore è l'istanza di Operatore.
     */
    private static final Map<String, Operatore> ISTANZE = new TreeMap<>();

    /**Il nome di questo Operatore.*/
    public final String nome;

    /**Il budget di questo Operatore.*/
    private int budget;

    /**Le azioni possedute da questo Operatore.
     * La chiave è l'azione, il valore è la quantità posseduta. 
     */
    private final Map<Azione, Integer> azioni = new TreeMap<>();

    /*-
     * AF:
     *  - nome è il nome di questo Operatore;
     *  - budget è il budget di questo Operatore;
     *  - azioni contiene come chiave l'azione posseduta da questo Operatore e come valore la loro quantità. 
     * 
     * RI:
     *  - nome non può null e non può essere vuoto o contenere solo spazi;
     *  - budget non può essere negativo;
     *  - azioni non può essere null e non può contenere nè null nè duplicati.
     *    Inoltre, i valori di azioni devono essere positivi e azioni deve essere ordinata lessicograficamente.
     */

    /**
     * Metodo di fabbricazione per ottenere un'istanza di Operatore.
     * 
     * <p>Se non esiste già un'operatore con il nome specificato, ne viene creata uno nuovo. 
     * Altrimenti viene restituita l'istanza già esistente.</p>
     * @param nome il nome dell'operatore.
     * @return l'istanza di Operatore.
     */
    public static Operatore of(final String nome) {
        if (Objects.requireNonNull(nome).isBlank())
            throw new IllegalArgumentException("Il nome dell'operatore non può essere vuoto");
        if (!ISTANZE.containsKey(nome)) ISTANZE.put(nome, new Operatore(nome));
        return ISTANZE.get(nome);
    }

    /**
     * Costruisce un'istanza di Operatore.
     * 
     * <p> Modifica this.
     * 
     * <p>Il costruttore è privato per garantire
     * che le istanze di Operatore siano create
     * solo tramite il metodo di fabbricazione {@link #of(String)}.</p>
     * 
     * @param nome il nome dell'operatore.
     * 
     */
    private Operatore(final String nome) {
        this.nome = nome;
        this.budget = 0;
    }

    /**
     * Restituisce il budget di questo Operatore.
     * 
     * @return il budget.
     */
    public int budget() {
        return budget;
    }

    /**
     * Restituisce una mappa 
     * contenente le azioni possedute da questo Operatore
     * e la loro quantità.
     * 
     * <p> La mappa restituita è una collezione non modificabile.
     * La chiave è l'azione, il valore è la quantità di azioni possedute
     * da questo Operatore.
     * 
     * @return le azioni e la quantità posseduta.
     */
    public Map<Azione, Integer> azioni() {
        return Collections.unmodifiableMap(azioni);
    }

    /**
     * Deposita un importo nel budget di questo Operatore, 
     * aumentandolo di conseguenza.
     * 
     * <p> Modifica this perché aumenta il budget di questo Operatore.
     * 
     * @param importo l'importo da depositare.
     * @throws IllegalArgumentException se l'importo è negativo.
     * 
     */
    public void deposito(final int importo) {
        if (importo <= 0)
            throw new IllegalArgumentException("L'importo del deposito deve essere positivo");
        budget += importo;
    }

    /**
     * Preleva un importo dal budget di questo Operatore, 
     * diminuendolo di conseguenza.
     * 
     * <p> Modifica this perché diminuisce il budget di questo Operatore.
     * 
     * @param importo l'importo da prelevare.
     * @throws IllegalArgumentException se l'importo è negativo o
     *  se il budget di questo Operatore non è sufficiente per poter 
     *  effettuare il prelievo richiesto. 
     */
    public void prelievo(final int importo) {
        if (importo <= 0)
            throw new IllegalArgumentException("L'importo del prelievo deve essere positivo");
        else if (budget < importo)
            throw new IllegalArgumentException("Budget non sufficiente per il prelievo");
        budget -= importo;
    }

    /**
     * Acquista delle azioni di un'azienda da una borsa.
     * 
     * <p> Questo metodo inoltre:
     *  <ul>
     *   <li> modifica this perhcé aggiorna il budget di questo Operatore, diminuendolo
     *      in base al prezzo delle azioni acquistate;</li>
     *   <li> modifica this anche perché aggiunge le azioni acquistate all'insieme delle azioni possedute
     *       da questo Operatore;</li>
     *   <li> modifica lo stato dell'azione acquistata nella borsa, aggiungendo
     *        questo Operatore alla lista degli acquirenti 
     *        o aggiornando la quantità di azioni possedute da questo Operatore.
     *  </ul>
     * 
     * @param borsa la borsa da cui acquistare le azioni dell'azienda.
     * @param azienda l'azienda di cui acquistare le azioni.
     * @param prezzo il prezzo delle azioni da acquistare.
     * @throws NullPointerException se la borsa o l'azienda sono {@code null}.
     * @throws IllegalArgumentException se il prezzo delle azioni da acquistare è negativo o pari a zero.
     *  
     */
    public void acquistaAzioni(final Borsa borsa, final Azienda azienda, final int prezzo) {
        Objects.requireNonNull(borsa, "La borsa non può essere null");
        Objects.requireNonNull(azienda, "L'azienda non può essere null");
        if (prezzo <= 0)
            throw new IllegalArgumentException("Il prezzo delle azioni da acquistare deve essere positivo");
        
        Azione azione = borsa.prendiAzione(azienda);
        int quantitaAzioni = prezzo / azione.prezzo(); 
        borsa.compraAzione(azione, this, quantitaAzioni);
        prelievo(quantitaAzioni * azione.prezzo());
        azioni.put(azione, azioni.getOrDefault(azione, 0) + quantitaAzioni);
        if (azioni.get(azione) == 0)
            azioni.remove(azione);
    }

    /**
     * Vende delle azioni di un'azienda possedute
     * da questo Operatore in una borsa.
     * 
     * <p> Questo metodo inoltre:
     *  <ul>
     *   <li> modifica this perché modifica il budget di questo Operatore, aumentandolo
     *        in base al prezzo delle azioni vendute;</li>
     *   <li> rimuove le azioni vendute dall'insieme delle azioni possedute 
     *        da questo Operatore;</li>
     *   <li> modifica lo stato dell'azione venduta nella borsa,
     *        aggiornando di conseguenza le azioni possedute da questo Operatore
     *        per l'azienda specificata.</li>
     * </ul>
     * 
     * @param borsa la borsa in cui vendere le azioni dell'azienda.
     * @param azienda l'azienda di cui vendere le azioni.
     * @param quantita la quantità di azioni che si desidera vendere.
     * @throws NullPointerException se la borsa o l'azienda sono {@code null}.
     * @throws IllegalArgumentException se la quantità di azioni da vendere è negativa.
     */
    public void vendiAzioni(final Borsa borsa, final Azienda azienda, final int quantita) {
        Objects.requireNonNull(borsa, "La borsa non può essere null");
        Objects.requireNonNull(azienda, "L'azienda non può essere null");
        if (quantita <= 0)
            throw new IllegalArgumentException("La quantità di azioni da vendere deve essere positiva");
        
        Azione azione = borsa.prendiAzione(azienda);
        int prezzo = azione.prezzo();
        borsa.vendiAzione(azione, this, quantita);
        deposito(prezzo * quantita);
        azioni.put(azione, azioni.getOrDefault(azione, 0) - quantita);
        if (azioni.get(azione) == 0) {
            azioni.remove(azione);
        }
    }

    /**
     * Calcola il valore delle azioni possedute da questo Operatore.
     * 
     * <p> Il valore delle azioni possedute da questo Operatore
     * è dato dalla somma del prezzo delle azioni possedute moltiplicato
     * per la quantità di azioni possedute.</p>
     * 
     * @return il valore delle azioni possedute da questo Operatore.
     * 
     */
    public int valoreAzioni() {
        int n = 0;
        for (Map.Entry<Azione, Integer> coppia : azioni.entrySet()) {
            n += coppia.getKey().prezzo() * coppia.getValue();
        }
        return n;
    }

    /**
     * Calcola il capitale totale di questo Operatore.
     * 
     * <p>Il capitale totale di questo Operatore è dato
     * dalla somma del suo budget e del 
     * valore delle azioni possedute.</p>
     * 
     * @return il capitale totale di questo Operatore.
     */
    public int capitaleTotale() {
        return budget + valoreAzioni();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nome).append(", ").append(budget).append(", ").append(valoreAzioni());
        for (Map.Entry<Azione, Integer> entry : azioni.entrySet()) {
            sb.append("\n- ")
              .append(entry.getKey().nomeBorsa()).append(", ")
              .append(entry.getKey().azienda().nome).append(", ")
              .append(entry.getValue());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Operatore o)) return false;
        return nome.equals(o.nome);
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }

    @Override
    public int compareTo(Operatore o) {
        return nome.compareTo(o.nome);
    }

}
