/*
 * Jacopo Del Granchio
 * #0xx 06.11.2019
 *
 * Dato un numero tra 1 e 365 lo converte nel giorno e mese corrispondente.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>

int main() {
  setlocale(LC_ALL, "");

  int n = 1, g = 0, m = 0;

  do {
    if (n <= 0) printf("Il numero deve essere compreso tra 1 e 365\n\n");

    printf("Dammi un numero tra 1 e 365: ");
    scanf("%d", &n);
  } while (n <= 0);

  for (int i = n; i > 0; i--, g++) {
    switch (m) {
      case 1:
      case 3:
      case 5:
      case 7:
      case 8:
      case 10:
      case 12:
        g %= 31;
        break;

      case 4:
      case 6:
      case 9:
      case 11:
        g %= 30;
        break;

      case 2:
        g %= 28;
        break;
    }

    if (g == 0) m++;
  }

  printf("Il numero %d corrisponde al %d.%d\n", n, g, m);

  // getchar();
  // system("pause");
  return 0;
}
