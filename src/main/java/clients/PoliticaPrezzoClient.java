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

import borsanova.politicaPrezzo.*;
import borsanova.Azienda;
import borsanova.Borsa;
import borsanova.Operatore;

import java.util.Scanner;

/** Client di test per alcune funzionalità relative alle <strong>borse</strong>. */
public class PoliticaPrezzoClient {

  /** . */
  private PoliticaPrezzoClient() {}

    /*-
   * Scriva un [@code main} che riceve come parametri sulla linea di comando
   *
   *      nome_borsa valore budget_iniziale
   *
   * il secondo parametro è un intero che determina la politica di prezzo della
   * borsa come segue: se è positivo, la politica è ad incremento costante pari
   * a tale valore, viceversa se è negativo, la politica è a decremento costante
   * pari al valore assoluto di tale valore.
   *
   * Il programma quindi legge dal flusso in ingresso una sequenza di due gruppi
   * di linee (separati tra loro dalla linea contenente solo --) ciascuno della
   * forma descritta di seguito:
   *
   *     nome_azienda numero prezzo_unitario
   *     ...
   *     --
   *     b nome_azienda prezzo_totale 
   *     ... [oppure] 
   *     s nome_azienda numero_azioni
   *
   * in base al contenuto del primo blocco, quota le azioni delle aziende
   * specificate nella borsa (definita dal primo parametro sulla linea di
   * comando) secondo il numero e prezzo unitario specificati, in base al
   * contenuto del secondo blocco — una volta creato un operatore (di nome
   * qualunque, con il budget iniziale specificato dal terzo parametro sulla
   * linea di comando) — esegue le operazioni a seconda che il carattere che
   * segue il nome dell'operatore sia: 
   *
   * - b compra azioni (dell'azienda specificata, impegnano il prezzo totale
   *   specificato),
   * - s vende azioni (dell'azienda specificata, nel numero specificato).
   *
   * Al termine della lettura il programma emette nel flusso d'uscita l'elenco
   * delle azioni (in ordine alfabetico) seguite dal prezzo (separato da una
   * virgola).
   */
  public static void main(String[] args) {
    Borsa borsa = Borsa.of(args[0]);
    int pp = Integer.parseInt(args[1]);
    PoliticaPrezzo polPrezzo = null;
    if (pp > 0)
      polPrezzo = new IncrementoCostante(pp);
    else if (pp < 0) 
      polPrezzo = new DecrementoCostante(-pp);
    borsa.politica(polPrezzo);
    Operatore op = Operatore.of("MarioRossi");
    op.deposito(Integer.parseInt(args[2]));
    try (Scanner sc = new Scanner(System.in)) {
      //quota le aziende nella borsa
      while (sc.hasNext()) {
        String nomeAzienda = sc.next();
        if (nomeAzienda.equals("--")) break;
        int numero = sc.nextInt();
        int prezzo = sc.nextInt();
        Azienda.of(nomeAzienda).quotaInBorsa(borsa, numero, prezzo);
      }
      //eseguo le operazioni
      while(sc.hasNext()) {
        String operazione = sc.next();
        Azienda azienda = Azienda.of(sc.next());
        int n = sc.nextInt();
        if (operazione.equals("b")) {
          op.acquistaAzioni(borsa, azienda, n);
        } else if (operazione.equals("s")) {
          op.vendiAzioni(borsa, azienda, n);
        }
  
      }
    }
    for (Borsa.Azione azione : borsa.azioni())
      System.out.println(azione.azienda().nome +  ", " + azione.prezzo());  
  }
}
