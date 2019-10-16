/*
 * Jacopo Del Granchio
 * #004
 *
 * Controlla se un numero è divisibile per 5 e 3.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

#define B 5
#define C 3

int a;

int main() {
  printf("Dammi un numero: ");
  scanf(" %d", &a);

  if (a % (B * C) == 0) printf("'%d' è divisibile per '%d' e '%d'.\n", a, B, C);
  else printf("'%d' non è divisibile per '%d' e '%d'.\n", a, B, C);

  // equivalente a system("pause") ma multipiattaforma. system("pause") è solo per windows.
  // https://stackoverflow.com/questions/9386651/pause-screen-at-program-completion-in-c
  getchar();
  return 0;
}
