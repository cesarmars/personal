# include <stdio.h>
int main(){
    int x = 10;
    int *p = &x;
    printf("Value of x: %d\n", *p);
    *p = 20;
    printf("New value of x: %d\n", x);
    return 0;
}