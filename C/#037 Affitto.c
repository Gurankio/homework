/*
 * Jacopo Del Granchio
 * #037 12.11.2019
 *
 * Calcola il costo totale dell'affitto di una vettura,
 * dati i giorni di utilizzo e la distanza percorsa.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>

#define GIORNO 15

int main() {
  setlocale(LC_ALL, "");

  int d, km;
  printf("%s", "Dimmi quanti giorni di utilizzo e la distanza percorsa: ");
  scanf("%d %d", &d, &km);

  double r = d * GIORNO;
  r += 0.30 * (km < 100 ? km : 100);
  r += 0.20 * (km - 100 > 0 ? km - 100 : 0);
  printf("%s%.2f\n", "Il costo totale è ", r);

  // getchar();
  // system("pause");
  return 0;
}
