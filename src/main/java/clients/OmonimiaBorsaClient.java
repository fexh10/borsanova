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

import borsanova.Borsa;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/** Client di test per alcune funzionalità relative alle <strong>aziende</strong>. */
public class OmonimiaBorsaClient {

  /** . */
  private OmonimiaBorsaClient() {}

  /*-
   * Scriva un {@code main} che legge dal flusso di ingresso una sequenza di
   * linee, ciascuna delle quali corrispondente ad un nome di borsa ed emette
   * nel flusso d'uscita l'elenco di tali nomi di borsa in ordine alfabetico e
   * senza ripetizioni.
   */

  /**
   * Metodo main per testare l'onominia della classe {@link Borsa}.
   * 
   * <p>Il metodo legge dal flusso di ingresso una sequenza di linee, ciascuna
   * delle quali corrispondente ad un nome di borsa ed emette nel flusso d'uscita
   * l'elenco di tali nomi di borsa in ordine alfabetico e senza ripetizioni.</p>
   * 
   * @param args non utilizzato.
   */
  public static void main(String[] args) {
    Set<Borsa> borse = new TreeSet<>();
    try (Scanner sc = new Scanner(System.in)) {
      while (sc.hasNextLine()) 
        borse.add(Borsa.of(sc.nextLine()));
    }
    for (Borsa b : borse) 
      System.out.println(b.nome);
  }
}
