/*
 * Jacopo Del Granchio
 * #020 22.10.2019
 *
 * Stampa la somma dei primi N numeri interi positivi.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int main() {
  int n, risultato = 1;

  do {
    printf("Dammi un numero: ");
    scanf("%d", &n);
  } while (n < 0);

  for (int i = 1; i <= n; ++i)
    risultato *= i;

  printf("Risultato: %d\n", risultato);

  // getchar();
  // system("pause");
  return 0;
}
