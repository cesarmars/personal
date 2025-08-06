age = int(input('What is your age?\n'))
# ✅ ↓ CHANGE THE CODE BELOW TO ADD 10 TO AGE ↓ ✅

# print("Your age is: "+str(age))


class Person:
    def __init__(self, name, age, height):
        self.name = name
        self.age = age
        self.height = height
        
me = Person("Cesar", 21, 160)
print(me.name)


    
   