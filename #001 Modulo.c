#include <stdio.h>

int a;

int main() {
  printf("Dammi un numero.\n");
  scanf(" %d", &a);
  printf("Il numero e' %s.\n", a % 2 == 0 ? "pari" : "dispari");

  // equivalente a system("pause") ma multipiattaforma. system("pause") è solo per windows.
  // https://stackoverflow.com/questions/9386651/pause-screen-at-program-completion-in-c
  getchar();
  return 0;
}
