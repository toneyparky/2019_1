
# coding: utf-8

# #이진탐색트리 구성하기
# ----------------------------------------------------------------------------
# 기능 : 
# 
# 학번순으로 출력하기
# 이름순으로 출력하기
# 
# 
# ## 필요한 것
# 클래스 : Node, BinarySearchTree (대부분의 기능은 안에서 구현)
# 
# 메서드 ->
# setRoot : root가 맨 처음 노드를 가리키게 한다.
# find : 학번을 input으로 받아 탐색 후 찾으면 학번과 이름을 출력하고 true 리턴 (step 2를 풀기 위함), findNode와 연결
# find2 : 학번을 input으로 받아 탐색 후 찾으면 이름을 리턴 (step 3를 풀기 위함), findNode와 연결
# findNode : 실제로 검색하는 메서드, 재귀로 구현, 더 작으면 leftchild 더 크면 rightchild로 같으면 그 값을 리턴!
# insert : 처음에 insert할 때는 setRoot, 그 이후에는 insertNode와 연결.
# insertNode : 실제로 insert하는 메서드, 재귀로 구현, 더 작으면 leftchild 더 크면 rightchild로. 적정위치를 찾으면 새로 만들어서 연결
# traverse : 중위순회하기, traverseNode와 연결
# traverseNode : 실제로 순회하는 메서드, result 리스트에 중위순회로 탐색하는 방식대로 추가한다.
# 
# 
# 
# ## 기능구현의 핵심
# 교수님께서 말씀해주신 이진탐색트리와 중위순회의 개념을 이용하여 트리를 만들고 순회한다.
# 

# In[3]:


'''
Step.1, 2, 3를 진행하며 원하는 값을 출력한다.
'''
import random
import string
import sys 

lim_num = 50000  #for test make it 10000
year = [2013, 2014, 2015, 2016, 2017, 2018, 2019]
    
b = BinarySearchTree()

std_array = [None]*1000  
std_array2 = [None]*1000  

#50000개의 학번, 이름, 전화번호를 저장하기
for i in range(lim_num):
    temp = str(random.choice(year)) + ''.join(random.choice(string.digits) for _ in range(5))
    #마지막 1000개의 학번을 따로 저장하기.
    if lim_num - (i + 1) > 48999: #for test make it 8999
        std_array[i] = temp
    b.insert(''.join(random.choice(string.ascii_lowercase) for _ in range(10)), temp, "010-" + ''.join(random.choice(string.digits) for _ in range(4)) + "-" + ''.join(random.choice(string.digits) for _ in range(4)))

    
print("Step 1. 중위 순회하기")
sorted_str = b.traverse()
for i in range(lim_num):
    if i == 0:
        print(sorted_str[i])
    else:
        #정렬이 잘 되어있는지 확인 
        if sorted_str[i-1] <= sorted_str[i]:
            temp = "(OK)"
        elif sorted_str[i-1] > sorted_str[i]:
            temp = "(ER)"
        print(str(sorted_str[i]) + " " + temp)


print("\n")
print("Step 2. 데이터 탐색하기")
#저장해두었던 마지막 1000개의 학번을 이용하여 탐색
for i in range(1000): 
    b.find(std_array[i])



print("\n")
print("Step 3. 랜덤한 학번으로 검색하기")
#랜덤한 1000개의 학번 생성
for i in range(1000):
    std_array2[i] = str(random.choice(year)) + ''.join(random.choice(string.digits) for _ in range(5))
    
temp_count = 0
for i in range(1000):
    result = b.find2(std_array2[i])
    if result != "(없음)":
        temp_count += 1
    print(str(i + 1) + ". " + str(std_array2[i]) + " " + result)
print("총 " + str(temp_count) + "개 발견")


    
    
    


# In[2]:


'''
이분검색트리
'''
class Node:
    def __init__(self, input_name, std_num, pho_num):
        self.name = input_name
        self.std_num = std_num
        self.pho_num = pho_num
        self.leftChild = None
        self.rightChild = None
        
class BinarySearchTree:
    def __init__(self):
        self.root = None
        self.count = 1
    #set root node
    def setRoot(self, name, std_num, pho_num):
        self.root = Node(name, std_num, pho_num)
        
    #for step2   
    def find(self, std_num):
        if self.findNode(self.root, std_num) == False:
            return False
        else:
            result = self.findNode(self.root, std_num)
            print(str(self.count) + ". " + str(std_num)+ " " + result.name )
            self.count += 1
            return True
        
    #for step3
    def find2(self, std_num):
        if self.findNode(self.root, std_num) == False:
            return "(없음)"
        else:
            result = self.findNode(self.root, std_num)
            return result.name
        
    def findNode(self, curNode, std_num):
        #원하는 값을 못찾은 경우
        if curNode == None:
            return False
        #찾음
        elif std_num == curNode.std_num:
            return curNode
        #왼쪽으로 탐색
        elif std_num < curNode.std_num:
            return self.findNode(curNode.leftChild, std_num)
        #오른쪽으로 탐색
        else:
            return self.findNode(curNode.rightChild, std_num)
        
    def insert(self, name, std_num, pho_num):
        #처음 삽입
        if self.root == None:
            self.setRoot(name, std_num, pho_num)
        #이후 삽입 
        else:
            self.insertNode(self.root, name, std_num, pho_num)
    
    def insertNode(self, curNode, name, std_num, pho_num):
        if std_num <= curNode.std_num:
            if curNode.leftChild: #leftchild가 None이 아니면 (더 내려가야지)
                self.insertNode(curNode.leftChild, name, std_num, pho_num)
            else:#실제 삽입
                curNode.leftChild = Node(name, std_num, pho_num)
        elif std_num > curNode.std_num:
            if curNode.rightChild: #rightchild가 None이 아니면 (더 내려가야지)
                self.insertNode(curNode.rightChild, name, std_num, pho_num)
            else:#실제 삽입
                curNode.rightChild = Node(name, std_num, pho_num)
        
    #탐색 
    def traverse(self):
        return self.traverseNode(self.root)
    
    #실제 탐색 - 중위순회대로 왼쪽 가운데 오른쪽 순서로 탐색한다.
    def traverseNode(self, curNode):
        result = []
        if (curNode.leftChild is not None): 
            result.extend(self.traverseNode(curNode.leftChild))
        if (curNode is not None):
            result.extend([curNode.std_num])
        if (curNode.rightChild is not None):
            result.extend(self.traverseNode(curNode.rightChild))
        return result
        
        
        

