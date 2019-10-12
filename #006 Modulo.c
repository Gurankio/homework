#include <stdio.h>

int a, b, c;

int main() {
  printf("Dammi tre numeri: ");
  scanf(" %d %d %d", &a, &b, &c);

  if (a % b == 0 && a % c != 0) printf("'%d' è divisibile per '%d' ma non per '%d'.\n", a, b, c);
  else printf("'%d' non è divisibile per '%d' e/o è divisibile per '%d'.\n", a, b, c);

  // equivalente a system("pause") ma multipiattaforma. system("pause") è solo per windows.
  // https://stackoverflow.com/questions/9386651/pause-screen-at-program-completion-in-c
  getchar();
  return 0;
}
