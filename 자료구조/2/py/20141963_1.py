
# coding: utf-8

# #전화번호 관리 프로그램
# ----------------------------------------------------------------------------
# 기능 : 
# 
# 이름으로 번호 찾기 (프로그램에 있는 이름을 입력하면)
# 
# 이름과 번호 추가하기 (프로그램에 없는 이름을 입력하면)
# 
# 이름으로 이름과 번호 삭제하기 (삭제 000-이름)
# 
# 이름순으로 정렬하기 (정렬)
# 
# 전체 이름과 번호 목록 보기 (목록)
# 
# 
# ## 필요한 것
# 클래스 : SinglyLinkedList (대부분의 기능은 안에서 구현)
# 
# 메서드 : size, is_empty, display, search, insert, delete, sort
# 
# 

# In[7]:


import random
import string
import sys 
s = SinglyLinkedList()

#insert default 10 data!
for i in range(10):
    s.insert(''.join(random.choice(string.ascii_lowercase) for _ in range(10)), "010-" + ''.join(random.choice(string.digits) for _ in range(4)) + "-" + ''.join(random.choice(string.digits) for _ in range(4)))
    
print("=========전화번호부=========") #start!
while True:
    print("(" + str(s.size) + "명의 데이터가 있습니다.)") #showing the number of data
    print("이름, 목록, 정렬, 삭제 이름, 종료  --- 이름은 영어 예) aeijkdfnkd\n")
    option = input()
    
    #display all Node data
    if option == "목록":
        s.display()
        
    #sorting by name & display
    elif option == "정렬":
        s.sort()
        s.display()
        continue
        
    #if has -> del, not has -> error
    elif option[:2] == "삭제": #get "삭제"
        del_name = option[3:] #get name
        if s.search(del_name) == False: #has not
            print("존재하지 않는 이름입니다.")
            continue
        else:
            print(del_name + " " + s.delete(del_name) + " 가 삭제되었습니다.")
            
    #exit
    elif option == "종료":
        print("종료합니다.")
        break

    #insert & find    
    else:
        #if not : insert
        if s.search(option) == False:
            print("존재하지 않는 이름입니다. 전화번호를 입력하세요.")
            num = input("전화번호 >>>")
            s.insert(option, num)
            print(option + " " + num + "가 입력되었습니다.")
        
        #if has(find) : std_num print
        else:
            print("(찾았습니다) " + s.search(option))
            


# In[6]:


'''단순 연결리스트 구현
    Node
    
    메서드들 : 
    size
    is_empty
    display
    search
    insert
    delete
    sort
'''
class SinglyLinkedList:
    # Node : save the data
    class Node:
        def __init__(self, name, number, next): 
            self.name = name
            self.number = number
            self.next = next
    
    def __init__(self):
        self.head = None
        self.size = 0
        
    def size(self):
        return self.size
    
    def is_empty(self):
        if self.size == 0:
            return True
        else:
            return False
        
    def display(self):
        p = self.head
        count = 1
        if self.is_empty():
            print('empty - display')
            return None
        
        while p:
            if p.next != None:
                print(str(count) + ". " + p.name + " " + p.number + "\n")
            else:
                print(str(count) + ". " + p.name + " " + p.number + "\n")
            count += 1
            p = p.next    
        
        
    def search(self, name):
        p = self.head
        
        for i in range(0, self.size):
            if p.name == name:
                return p.number
            else:
                p = p.next
        return False
        
        
        #insert after the last node
    def insert(self, name, number):
        p = self.head
        if self.is_empty():
            self.head = self.Node(name, number, None)
            self.size += 1
            return None
            
        while p.next:
            p = p.next 
        p.next = self.Node(name, number, None)
        self.size += 1        
        
    def delete(self, name):
        p = self.head

        if self.is_empty():
            print("ERROR : empty - delete", name)
            return None
        
        #delete the first node
        if p.name == name:
            temp = p.number
            self.head = p.next
            self.size -= 1
            return temp
        
        #delete func
        for i in range(0, self.size):
            if p.next.name == name:
                temp = p.next.number
                p.next = p.next.next
                self.size -= 1
                return temp
            else:
                p = p.next
        return False
        
        
        
    #p, q, r을 두고 p는 기준으로, q는 p.next, r은 p.next.next로.두어서 q와 r을 바꾸기로한다.
    def sort(self):
        p = self.head
        for i in range(0, self.size):
            p = self.head
            for j in range(0, self.size - i): 
                q = p.next
                
                if p.next == None:
                    break
                
                if p.next.next == None: #우리는 항상 p말고 그 다음다음을 바꾸기 때문
                    break
                
                #처음노드를 바꾼다. (p와 q)
                if j == 0:
                    if p.name < q.name:
                        continue
                    else:
                        p.next = q.next
                        q.next = p
                        self.head = q
                        continue
                        
                r = q.next  #이제부터 p, q, r로 돌아간다 p는 기준이되는 앞으로 계속 가는거고, q와 r이 바뀐다.
                #r의 다음이 없을 경우 : q.next를 None으로 설정해준다.   
                if p.next.next.next == None:
                    if q.name > r.name:
                        r.next = q
                        q.next = None
                        p.next = r
                        continue
                    else:
                        continue
                #기준이되는 p를 한 칸씩 오른쪽으로 가게한다.   
                if q.name < r.name:
                    p = p.next
                    continue
                else: 
                    #sort하기
                    q.next = r.next
                    r.next = q
                    p.next = r
                p = p.next

