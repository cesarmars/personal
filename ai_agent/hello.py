class Dog:
    def __init__(self):
        self.name = "Carson"
        self.age = 10
        self.breed = "Golden Retriever"

    def bark(self):
        return "Woof!"
    
    def main(self):
        print("Name: " + self.name)
        print("Age: " + str(self.age))
        print("Breed: " + self.breed)
        print(self.bark())

Dog().main()