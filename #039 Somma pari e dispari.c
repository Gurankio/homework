/*
 * Jacopo Del Granchio
 * #039 12.11.2019
 *
 * Di una sequanza calcola la somma fra i numri pari e quelli dispari.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>

int main() {
  setlocale(LC_ALL, "");

  int n;
  printf("Quanti numeri? ");
  scanf("%d", &n);

  int p = 0, d = 0;

  for (int i = 0; i < n; i++) {
    int t;
    printf("Dammi un numero: ");
    scanf("%d", &t);

    if (t % 2 == 0) p += t;
    else d += t;
  }

  printf("La somma dei pari è %d quella dei dispari è %d\n", p, d);;

  // getchar();
  // system("pause");
  return 0;
}
