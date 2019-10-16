/*
 * Jacopo Del Granchio
 * #013 16.10.19
 *
 * Una smeplice calcolatrice che supporta le operazion: +, -, * e /.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int main() {
  char operazione;
  int a, b;

  printf("Dammi un' operazione: ");
  scanf("%d %c %d", &a, &operazione, &b);

  switch (operazione) {
    case '+':
      printf("Il risultato è: %d\n", a + b);
      break;

    case '-':
      printf("Il risultato è: %d\n", a - b);
      break;

    case '*':
      printf("Il risultato è: %d\n", a * b);
      break;

    case '/':
      printf("Il risultato è: %d\n", a / b);
      break;

    default:
      printf("Operazione inesistente.\n");
  }

  // getchar();
  // system("pause");
  return 0;
}
