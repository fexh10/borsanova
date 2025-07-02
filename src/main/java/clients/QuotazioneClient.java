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

/**
 * Client di test per alcune funzionalità relative alle
 * <strong>quotazioni</strong>.
 */
public class QuotazioneClient {

  /** . */
  private QuotazioneClient() {
  }

  /*-
   * Scriva un {@code main} che legge dal flusso di ingresso una sequenza di
   * linee della forma
   *
   *    nome_azienda nome_borsa quantità prezzo
   *
   * Assuma che i nomi non contengano spazi. Dopo aver quotato le aziende nelle
   * borse emette nel flusso d'uscita, per ciascuna borsa, l'elenco di aziende
   * in essa quotate; i nomi delle borse e delle aziende devono essere uno per
   * linea, in ordine alfabetico, e i nomi di azienda devono essere prefissati
   * da "- ". 
   */
  public static void main(String[] args) {
    Set<Borsa> borse = new TreeSet<>();
    Set<Azienda> aziende = new TreeSet<>();
    try (Scanner sc = new Scanner(System.in)) {
      while (sc.hasNext()) {
        Azienda azienda = Azienda.of(sc.next());
        aziende.add(azienda);
        Borsa b = Borsa.of(sc.next());
        borse.add(b);
        int quantita = sc.nextInt();
        int prezzo = sc.nextInt();
        azienda.quotaInBorsa(b, quantita, prezzo);
      }
    }
    for (Azienda a : aziende)
      System.out.println(a.toString());
    for (Borsa b : borse) {
      System.out.println(b.nome);
      for (Borsa.Azione a : b.azioni())
      System.out.println("- " + a.azienda().nome);
    }
  }
}
