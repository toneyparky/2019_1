
# coding: utf-8

# #Making a calculator!!
# 
# ----------------------------------------------------------------------------
# ##우선순위 처리하기 :
# )가 최우선
# **가 2순위
# %, / * 가 3순위
# + - 가 4순위
# ( 가 5순위
# ----------------------------------------------------------------------------
# ##1000 이하의 정수 처리하기 :
# 숫자를 하나 읽으면 그 다음도 읽고 그게 숫자면 계속 읽고 아니면 연산자 읽는 것으로 바꾸고 없으면 끝인걸 표시
# 
# 
# 연산자 읽을 때에 처리하기 :
# 
# * 케이스만 따로 두자
# *는 다음에 *가 또 있으면 **라고 인식하고 S로 바꾸어주기 (상수로 둘까)
# 
# S 를 보면 **로 처리~~~
# 
# 
# ----------------------------------------------------------------------------
# ##필요한 것
# 
# 메서드 : infixToPostfix 
#         calPostfix
#         errorDetection
# 
# 변수 : input 저장할 리스트
#         infixToPostfix의 결과값인 postfix를 저장할 리스트
#         연산자를 저장할 stack (myStack이용)
#         계산값 출력할 result
#         연산자 **를 대체할 S 상수..? 혹은 그냥 이렇게 안해도 나만의 룰로 주석처리 해도 괜찮을 듯
#         등등..
#         
# ----------------------------------------------------------------------------
# ##Flow
# 총 세개의 메서드를 사용한다.
# (errorDetection, infixToPostfix, claPostfix)
# 
# 1. errorDetection :
# User로 부터 input 받아서 errorDetection으로 넘겨주자!
# error가 있으면 출력 후 강제종료
# 없으면 step 2로!
# 
# 2. get a input expression :
# 인풋 받은 것이 오류가 없으므로 infixToPostfix 메서드로 넘겨주는 단계 (메인에서 실행)
# 
# 3. change infix to postfix :
# infixToPostfix 메서드에서 진행
# 피연산자를 만나면 그대로 postfix에 저장
# 연산자를 만나면 스택에 저장. stack (스택 Top보다 우선 순위가 낮은 연산자가 나오면 그때 postfix에 저장 )
# (왼쪽 괄호는 우선 순위가 가장 낮은 연산자)
# (오른쪽 괄호가 나오면 스택에서 왼쪽 괄호 위에 쌓여있는 모든 연산자를 postfix에 저장)
# 끝나면 postfix를 return
# 
# 4. 메인에서  calPostfix로 postfix를 전달
#     
# 5. calPostfix : 
# (숫자처리, 연산자 **처리)
# calPostfix 메서드에서 계산 진행 후 결과값을 return
# 
# 6. 메인에서 결과값 출력
# 
# 

# In[257]:




# In[256]:


'''Calculation Program
main 함수로서 Calculation을 구동하는 cell
'''
def main():
    import sys
    print("-------계산기-------")
    print("규칙 : 1000이하의 정수")
    print("규칙 : 사용가능한 연산자 -> **, *, %, /, +, -, (, )")
    print("규칙 : 계산을 원하는 식을 다음의 방식으로 입력하시오 -> 예) 3+5*(50-3**2)-1 ")
    expression = input() #자동으로 스트링이 되긴함

    errorDetect = errorDetection()

    error_result = errorDetect.detect(expression)

    if error_result == None:
        pass
    else:
        print(error_result)
        sys.exit("error")

    inToPost = infixToPostfix()
    postfix = inToPost.trans(expression)

    calculation = calPostfix()
    result = calculation.cal(postfix)

    print("계산식: ", expression)
    print("결과: ", result[0])


# 
# #TEST CASE 
# 
# 정상1 : (11+3)*2**3-12 = 100
# 정상2 : 44+22**(231-39*33)-33%2**4 = 43
# 정상3 : 44+(231-39)*33-6/2+33 = 6410
# 정상4 : 7-3-4*2-43 = -47
# 정상5 : 2+(3*4)**2-12 = 134
# 
# 오류0 : 1010+1
# 오류1 : +3+4*2
# 오류2 : 3+4*(2+2**2))-3
# 오류3 : 3+4*(2+(2**2-3)
# 오류4 : 3+4*(2+(2**+2-3))
# 오류5 : 3+4+(2+(2*+2-3))
# 오류6 : 3+4+(2+(2-+2-3))
# 
# 

# In[250]:


'''Calculation Program
A method that detect errors!
Frist step of the flow (check above flow explanation section)
'''
class errorDetection:
    def __init__(self):
        self.stack = myStack()
        self.stack_2 = myStack()
        self.stack_3 = myStack()

        
    def detect(self,items):
        
        #연산자로 시작하는 오류
        if items[0] == "*" or items[0] == "%" or items[0] == "/" or items[0] == "+" or items[0] == "-":
            warning = items[0] + "(!)" + items[1:] + " 이 위치에 오류가 있습니다. 연산자로 시작합니다"
            print("오류1")
            return warning
        
        
        #괄호 관련 오류
        for index, item in enumerate(items):
            if item == "(":
                self.stack.push("(")
                self.stack_2.push(index)
            elif item == ")":
                if self.stack.isEmpty():
                    warning = items[0 : index + 1] + "(!)" + items[index + 1 :] + "이 위치에 오류가 있습니다. )가 하나 더 있습니다."
                    print("오류2")  #"3+4*(2+2**2))-3" 이런 케이스 해결
                    return(warning)
                self.stack.pop()
                self.stack_2.pop()
                
        if self.stack.isEmpty() == False: #괄호 관련 처리를 다 끝냈는데 stack에 뭐가 남아있으면 )가 부족한 것 
            print("오류3")      #"3+4*(2+(2**2-3)" 이런 케이스 해결
            warning = items + "(!)" + "이 위치에 오류가 있습니다. )가 부족합니다."  #다른데에 넣어야함 오류
            return warning
        
        for index, item in enumerate(items):
            if item == "*":
                if items[index + 1] == "*":
                    if items[index + 2] == "*" or items[index + 2] == "%" or items[index + 2] == "/" or items[index + 2] == "+" or items[index + 2] == "-":
                        warning = items[: index + 3] + "(!)" + items[index + 3 : ] + "이 위치에 오류가 있습니다. 연산자가 중복되어 나옵니다."
                        print("오류4") # "3+4+(2+(2**+2-3))" 같은 케이스 해결 (** 뒤 연산자)
                        return warning
        for index, item in enumerate(items):
            if item == "*":
                if items[index + 1] == "*":
                    temp_list = list(items)
                    temp_list[index] = "*"
                    temp_list[index + 1] = "S"
                    items = ''.join(temp_list)
                elif items[index + 1] == "S" or items[index + 1] == "%" or items[index + 1] == "/" or items[index + 1] == "+" or items[index + 1] == "-":
                    warning = items[: index + 2] + "(!)" + items[index + 2 :] + "이 위치에 오류가 있습니다. 연산자가 중복되어 나옵니다."
                    print("오류5") #"3+4+(2+(2*+2-3))" 같은 케이스 해결 (*뒤 연산자)
                    return warning
                #elif:
                    
                else:
                    continue
            elif item == "S" or item == "%" or item == "/" or item == "+" or item == "-":
                if items[index + 1] == "S" or items[index + 1] == "%" or items[index + 1] == "/" or items[index + 1] == "+" or items[index + 1] == "-":
                    warning = items[: index + 2] + "(!)" + items[index + 2 :] + "이 위치에 오류가 있습니다. 연산자가 중복되어 나옵니다."
                    print("오류6") #"3+4+(2+(2-+2-3))" 같은 케이스 해결 (이외의 연산자 뒤 연산자)
                    return warning


# In[253]:


'''Calculation Program
A method that exchange input (infix) to output (postfix)
Thrid step of the flow (check above flow explanation section)
'''
import sys
class infixToPostfix: 
    def __init__(self): #get list as a parameter
        self.postfix = [] #save the result
        self.stack = myStack()
        
    def trans(self, items):
        items = str(items)+ " " # " " -> mark the end point
        items_copy = items[:] #just wanna keep the input
        priority = {"S": 4, "*" : 3, "/" : 3, "%" : 3, "+" : 2, "-" : 2, "(" : 1, ")" : 5}

        for index, item in enumerate(items):
            #tracker
            '''print("------------------")
            print("item: ", item)
            print("items: ", items)
            print("postfix: ", self.postfix)
            print("stack: ", self.stack.show())
            print("------------------")'''
            
            if item != items[index]: #compare origin(enumerating string) and changed one (by blank or "!")
                continue

            if item == " ": #check the string is finished and if exception control : if user puts 3 + 4.
                continue
                
            elif item == "*":      #deal with **, ** -> "S"
                if items[index + 1] == "*":
                    self.stack.push("S")
                    temp_list = list(items)
                    temp_list[index + 1] = "!"
                    items = ''.join(temp_list)
                    continue
                else:
                    self.stack.push("*")
                    continue
                    
            elif item == "/":
                if self.stack.isEmpty() == True:
                    self.stack.push(item)
                    continue
                else:
                    if priority[self.stack.peek()] >= priority[item]:
                        while(priority[self.stack.peek()] >= priority[item]):
                            self.postfix.append(self.stack.pop())
                            if self.stack.isEmpty() == True:
                                break
                        self.stack.push(item)
                        continue
                    else:
                        self.stack.push(item)
                        continue
                
            elif item == "%":
                if self.stack.isEmpty() == True:
                    self.stack.push(item)
                    continue
                else:
                    if priority[self.stack.peek()] >= priority[item]:
                        while(priority[self.stack.peek()] >= priority[item]):
                            self.postfix.append(self.stack.pop())
                            if self.stack.isEmpty() == True:
                                break
                        self.stack.push(item)
                        continue
                    else:
                        self.stack.push(item)
                        continue
                
            elif item == "+":
                if self.stack.isEmpty() == True:
                    self.stack.push(item)
                    continue
                else:
                    if priority[self.stack.peek()] >= priority[item]:
                        while(priority[self.stack.peek()] >= priority[item]):
                            self.postfix.append(self.stack.pop())
                            if self.stack.isEmpty() == True:
                                break
                        self.stack.push(item)
                        continue
                    else:
                        self.stack.push(item)
                        continue
                
            elif item == "-":
                if self.stack.isEmpty() == True:
                    self.stack.push(item)
                    continue
                else:
                    if priority[self.stack.peek()] >= priority[item]:
                        while(priority[self.stack.peek()] >= priority[item]):
                            self.postfix.append(self.stack.pop())
                            if self.stack.isEmpty() == True:
                                break
                        self.stack.push(item)
                        continue
                    else:
                        self.stack.push(item)
                        continue

            elif item == "(":
                self.stack.push("(")
                continue
            
            elif item == ")":
                while(self.stack.peek() != "("):
                    self.postfix.append(self.stack.pop())
                self.stack.pop() # ( 제 거
                temp_list = list(items)
                temp_list[index] = " "
                items = ''.join(temp_list)
                continue

            else: #피연산자 처리
                if items[index + 1] == " ":
                    self.postfix.append(item)
                    continue
                #피연산자 다음의 char이 연산자가 아니라면
                elif items[index + 1] != "*" and items[index + 1] != "/" and items[index + 1] != "S" and items[index + 1] != "+" and items[index + 1] != "-" and items[index +1] != ")" and items[index + 1] != "%":
                    #그리고 피연산자 다음 다음 char이 " "라면 (끝이라면) -> 10의자리까지 처리
                    if items[index + 2] == " ":
                        number = 10 * int(item) + int(items[index + 1])
                        temp_list = list(items)
                        temp_list[index + 1] = "!"
                        items = ''.join(temp_list)
                        self.postfix.append(str(number))
                        continue
                    #그리고 피연산자 다음 다음 char이 연산자가 아니라면
                    elif items[index + 2] != "*" and items[index + 2] != "/" and items[index + 2] != "S" and items[index + 2] != "+" and items[index + 2] != "-" and items[index +2] != ")" and items[index + 2] != "%":
                        #그리고 피연산자 다음 다음 다음 char이 " "라면 (끝이라면) ->100의자리까지 처리
                        if items[index + 3] == " ":
                            number = 100 * int(item) + 10 * int(items_copy[index + 1]) + int(items_copy[index + 2]) #items_copy를 써서 items의 요소가 !로 바뀌어도 처리가능
                            temp_list = list(items)
                            temp_list[index + 1] = "!"
                            temp_list[index + 2] = "!"
                            items = ''.join(temp_list)
                            self.postfix.append(str(number))
                            continue
                        #그리고 피연산자 다음 다음 다음 char이 연산자가 아니라면 -> 숫자인데 천의자리까지 처리
                        elif items[index + 3] != "*" and items[index + 3] != "/" and items[index + 3] != "S" and items[index + 3] != "+" and items[index + 3] != "-" and items[index +3] != ")" and items[index + 3] != "%":
                            #피연산자 다음 다음 다음 다음 char이 연산자가 아니라면 -> 만의 자리시 오류 발생
                            if items[index + 4] != "*" and items[index + 4] != "/" and items[index + 4] != "S" and items[index + 4] != "+" and items[index + 4] != "-" and items[index +4] != ")" and items[index + 4] != "%":
                                #피연산자 다음 다음 다음 다음 다음 char이 연산자가 아니라면 -> 십만의 자리시 오류 발생
                                if items[index + 5] != "*" and items[index + 5] != "/" and items[index + 5] != "S" and items[index + 5] != "+" and items[index + 5] != "-" and items[index +5] != ")" and items[index + 5] != "%":
                                    warning = items_copy[0:index + 6] + "(!)" + items_copy[index + 6:] + "이 위치에 오류가 있습니다. 입력값 중 1000이 넘는 수가 있습니다."
                                    print("오류0")
                                    print(warning)
                                    sys.exit("error")
                                warning = items_copy[0:index + 5] + "(!)" + items_copy[index + 5:] + "이 위치에 오류가 있습니다. 입력값 중 1000이 넘는 수가 있습니다."
                                print("오류0")
                                print(warning)
                                sys.exit("error")
                            else:
                                pass
                            number = 1000 * int(item) + 100 * int(items_copy[index + 1]) + 10 * int(items_copy[index + 2]) + int(items_copy[index + 3]) #items_copy를 써서 items의 요소가 !로 바뀌어도 처리가능
                            temp_list = list(items)
                            temp_list[index + 1] = "!"
                            temp_list[index + 2] = "!"
                            temp_list[index + 3] = "!"
                            items = ''.join(temp_list)
                            self.postfix.append(str(number))
                            #error0 number is over 1000
                            if number > 1000: 
                                warning = items_copy[0:index + 4] + "(!)" + items_copy[index + 4:] + "이 위치에 오류가 있습니다. 입력값 중 1000이 넘는 수가 있습니다."
                                print("오류0")
                                print(warning)
                                sys.exit("error")
                            #number is exactly 1000
                            else:
                                continue 
                        #피연산자 다음 다음 char이 ")"아닐 때 -> 100의 자리까지 저장하기
                        elif items[index + 2] != ")":
                            number = 100 * int(item) + 10 * int(items_copy[index + 1]) + int(items_copy[index + 2]) #items_copy를 써서 items의 요소가 !로 바뀌어도 처리가능
                            temp_list = list(items)
                            temp_list[index + 1] = "!"
                            temp_list[index + 2] = "!"
                            items = ''.join(temp_list)
                            self.postfix.append(str(number))
                            continue
                    #10의 자리까지 저장하기
                    number = 10 * int(item) + int(items_copy[index + 1]) #items_copy를 써서 items의 요소가 !로 바뀌어도 처리가능
                    temp_list = list(items)
                    temp_list[index + 1] = "!"
                    items = ''.join(temp_list)
                    self.postfix.append(str(number))
                    continue
                #1의 자리 저장하기
                self.postfix.append(item) #피연산자
                continue
                
        while(not self.stack.isEmpty()):
            self.postfix.append(self.stack.pop())

        return self.postfix


# In[170]:


'''Calculation Program
A method that get an input(postfix) and calculate it (output)!
Fifth step of the flow (check above flow explanation section)
'''
class calPostfix:
    def __init__(self):
        self.stack = myStack() #set a stack
        
    def cal(self, items):
        for index, item in enumerate(items):
            if item != "S" and item != "*" and item != "/" and item != "%" and item != "+" and item != "-":
                self.stack.push(item)
            elif item == "S":
                num1 = self.stack.pop()
                num2 = self.stack.pop()
                result = int(num2) ** int(num1)
                self.stack.push(result)
            elif item == "*":
                num1 = self.stack.pop()
                num2 = self.stack.pop()
                result = int(num2) * int(num1)
                self.stack.push(result)
            elif item == "/":
                num1 = self.stack.pop()
                num2 = self.stack.pop()
                result = int(num2) / int(num1)
                self.stack.push(result)
            elif item == "%":
                num1 = self.stack.pop()
                num2 = self.stack.pop()
                result = int(num2) % int(num1)
                self.stack.push(result)
            elif item == "+":
                num1 = self.stack.pop()
                num2 = self.stack.pop()
                result = int(num2) + int(num1)
                self.stack.push(result)
            elif item == "-":
                num1 = self.stack.pop()
                num2 = self.stack.pop()
                result = int(num2) - int(num1)
                self.stack.push(result)
                
        return self.stack.show()        

# Set MyStack Class : 
 ##method : create, push, pop, peek, is_empty, show, length
# In[165]:


class myStack:
    def __init__(self):         #create an empty stack (list)
        self.stack = []

    def push(self, item):       #push an item to the stack
        self.stack.append(item)
    
    def pop(self):              #pop an item from the stack
        try:
            return self.stack.pop()
        except IndexError:
            print("Stack is empty")
        
    def isEmpty(self):          #check the stack is empty or not
        return len(self.stack) == 0 #!! no need to make "isFull" (there is no ending point in the stack)
        
    def peek(self):             #get an item from the stack without delete it
        if len(str(self.stack)) == 0:
            raise Exception("Stack empty!")
        else:
            return self.stack[-1]  
    
    def show(self):             #show all items from the stack
        return self.stack
        


# In[174]:


#stack checking

s = myStack()
print(s.isEmpty())
s.push(2)
s.push("안녕")
s.push(4)
print(s.show())
print(s.pop())
print(s.peek())
print(s.pop())
print(s.isEmpty())

print(type(3))

k = "3+4* 33+6-3"
j = [1, 2, 3]
for i in j:
    print(i)
    
for i, num in enumerate(k):

    print(num)
    print(type(num))


    

priority = {"S": 4, "*" : 3, "/" : 3, "%" : 3, "+" : 2, "-" : 2, "(" : 1, ")" : 5}
print("====")
print(priority["S"])
print(priority["%"])

