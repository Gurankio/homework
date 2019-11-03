/*
 * Jacopo Del Granchio
 * #000 GG.MM.YYYY
 *
 * Lorem ipsum dolor sit amet, consectetur adipisicing elit,
 * sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>

int main() {
  setlocale(LC_ALL, "");

  int n, t, risultato = 0;

  printf("Quanti numeri? ");
  scanf("%d", &n);

  for (int i = 0; i < n; ++i) {
    printf("Dammi un numero: ");
    scanf("%d", &t);

    risultato += t;
  }

  printf("La media è %.2f\n", (float)risultato / n);

  // getchar();
  // system("pause");
  return 0;
}
