// Dati tre numeri gli scrive in ordine decrescente.

#include <stdio.h>

int main() {
  int a, b, c, min, mid, max;

  printf("Dammi tre numeri.\n");
  scanf(" %d %d %d", &a, &b, &c);

  if (a < b) {
    min = a;
    max = b;
  } else {
    min = b;
    max = a;
  }

  if (c < min) {
    mid = min;
    min = c;
  } else {
    if (c > max) {
      mid = max;
      max = c;
    } else mid = c;
  }

  printf("Ordine Decrescente.\n");
  printf("%d, %d, %d\n", max, mid, min);

  getchar();
  return 0;
}
