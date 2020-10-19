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
  int n, s = 0;

  printf("Quanti numeri interi? ");
  scanf("%d", &n);

  /* int i = 0;
   * while () {
   *  [...]
   *  ++i;
   * }
   */

  for (int i = 1; i <= n; ++i)
    s += i;

  printf("Risultato: %d\n", s);

  // getchar();
  // system("pause");
  return 0;
}
