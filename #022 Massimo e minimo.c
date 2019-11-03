/*
 * Jacopo Del Granchio
 * #022 22.10.2019
 *
 * Calcola il massimo e il minimo di N numeri in input.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>

int main() {
  setlocale(LC_ALL, "");

  int n, t, max = 0, min = INT_MAX;

  printf("Quanti numeri? ");
  scanf("%d", &n);

  /* int i = 0;
   * while () {
   *  [...]
   *  ++i;
   * }
   */

  for (int i = 0; i < n; ++i) {
    printf("Dammi un numero: ");
    scanf("%d", &t);

    if (t > max) max = t;

    if (t < min) min = t;
  }

  printf("Il massimo è %d\n", max);
  printf("Il minimo è %d\n", min);

  // getchar();
  // system("pause");
  return 0;
}
