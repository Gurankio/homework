/*
 * Jacopo Del Granchio
 * #023 22.10.2019
 *
 * Calcola il fattoriale di un numero in input.
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

  /* int i = 0;
   * while () {
   *  [...]
   *  ++i;
   * }
   */

  for (int i = 1; i <= n; ++i)
    risultato *= i;

  printf("Risultato: %d\n", risultato);

  // getchar();
  // system("pause");
  return 0;
}
