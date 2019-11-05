/*
 * Jacopo Del Granchio
 * #036 06.11.2019
 *
 * Calcola il MCD tra 2 numeri in input.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>

int main() {
  setlocale(LC_ALL, "");

  int a, b, r = 0;

  printf("%s", "Dammi un numero: ");
  scanf("%d", &a);

  printf("%s", "Dammi un numero: ");
  scanf("%d", &b);

  if (a > b) {
    int t = a;
    a = b;
    b = t;
  }

  for (int i = 1; i <= a; i++)
    if (a % i == 0 && b % i == 0) r = i;

  printf("%s%d\n", "Il MCD è ", r);

  // getchar();
  // system("pause");
  return 0;
}
