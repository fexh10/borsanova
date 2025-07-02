/*

Copyright 2024 Massimo Santini

This file is part of "Programmazione 2 @ UniMI" teaching material.

This is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This material is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this file.  If not, see <https://www.gnu.org/licenses/>.

*/

package clients;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import borsanova.Azienda;
import borsanova.Borsa;
import borsanova.Operatore;

/**
 * Client di test per alcune funzionalità relative alle <strong>borse</strong>.
 */
public class BorsaClient {

  /** . */
  private BorsaClient() {
  }

  /*-
   * Scriva un [@code main} che legge dal flusso in ingresso una sequenza di tre
   * gruppi di linee (separati tra loro dalla linea contenente solo --) ciascuno
   * della forma descritta di seguito:
   *
   *     nome_azienda nome_borsa numero prezzo_unitario
   *     ...
   *     --
   *     nome_operatore budget_iniziale
   *     ...
   *     --
   *     nome_operatore b nome_borsa nome_azienda prezzo_totale 
   *     ... [oppure]
   *     nome_operatore s nome_borsa nome_azienda numero_azioni 
   *
   * Assuma che i nomi non contengano spazi. Iqn base al contenuto del primo
   * blocco, quota le azioni delle aziende nelle borse secondo il numero e
   * prezzo unitario specificati, in base al secondo blocco crea gli operatori
   * specificati con il budget iniziale specificato e in base al terzo blocco
   * esegue le operazioni, a seconda che il carattere che segue il nome
   * dell'operatore sia: 
   *
   * - b compra azioni (quotate nella borsa e dell'azienda specificata,
   *   impegnano il prezzo totale specificato),
   * - s vende azioni (quotate nella borsa e dell'azienda specificata, nel
   *   numero specificato).
   *
   * Osservi che l'acquisto può determinare un resto, nel caso in cui il prezzo
   * totale non sia un multiplo esatto del prezzo dell'azione; tale resto rimane
   * a disposizione dell'operatore per eventuali operazioni successive.
   *
   * Al termine della lettura il programma emette nel flusso d'uscita (una per
   * linea) l'elenco delle borse coinvolte (in ordine alfabetico), per ogni
   * borsa emette l'elenco delle azioni in essa quotate (in ordine alfabetico,
   * prefissate da -), e per ognuna di esse i nomi degli operatori e delle
   * quantità che ne possiedono (in ordine alfabetico, prefissati da =). 
   */
  public static void main(String[] args) {
    Set<Borsa> borse = new TreeSet<>();
    try (Scanner sc = new Scanner(System.in)) {

      // quota aziende in borse
      while (sc.hasNext()) {
        String nomeAzienda = sc.next();
        if (nomeAzienda.equals("--"))
          break;
        Borsa b = Borsa.of(sc.next());
        borse.add(b);
        int numero = sc.nextInt();
        int prezzo = sc.nextInt();
        Azienda.of(nomeAzienda).quotaInBorsa(b, numero, prezzo);
      }
      // istanza gli operatori
      while (sc.hasNext()) {
        String nomeOperatore = sc.next();
        if (nomeOperatore.equals("--"))
          break;
        int budget = sc.nextInt();
        Operatore op = Operatore.of(nomeOperatore);
        op.deposito(budget);
      }

      // eseguo le operazioni
      while (sc.hasNext()) {
        Operatore op = Operatore.of(sc.next());
        String operazione = sc.next();
        Borsa b = Borsa.of(sc.next());
        Azienda a = Azienda.of(sc.next());
        int valore = sc.nextInt();
        if (operazione.equals("b")) {
          op.acquistaAzioni(b, a, valore);
        } else {
          op.vendiAzioni(b, a, valore);
        }
      }
    }
    for (Borsa b : borse) 
      System.out.println(b.toString());
    
  }
}
