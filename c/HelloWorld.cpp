#include <iostream>

int main() {
    // Declare integer variables to store the numbers and their sum
    int num1, num2, sum;

    // Prompt the user to enter the first number
    std::cout << "Enter the first number: ";
    // Read the first number from the user
    std::cin >> num1;

    // Prompt the user to enter the second number
    std::cout << "Enter the second number: ";
    // Read the second number from the user
    std::cin >> num2;

    // Calculate the sum using the addition operator
    sum = num1 + num2;

    // Display the result
    std::cout << "The sum is: " << sum << std::endl;

    return 0; // Indicate successful program execution
    
    // std::cout << "Hello World!" << std::endl;
    // return 0;
}