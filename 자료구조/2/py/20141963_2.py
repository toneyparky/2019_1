
# coding: utf-8

# #연결리스트 - 응용
# ----------------------------------------------------------------------------
# 기능 : 
# 
# 학번순으로 출력하기
# 이름순으로 출력하기
# 
# 
# ## 필요한 것
# 클래스 : SinglyLinkedList (대부분의 기능은 안에서 구현)
# 
# 메서드 : size, is_empty, display_by_name, display_by_stdnum, insert
# 
# 
# ## 기능구현의 핵심
# insert메서드에 학번과 이름 그리고 전화번호가 들어갈 때에, head를 두 개 만든다. 각각 학번 (std_head), 이름(name_head).
# Node에도 다음 학번을 가리키는 name_next와 다음 이름을 가리키는 std_next 두개를 만든다.
# 
# 새로운 데이터가 insert될 때마다 필요시에 학번과 이름에 해당하는 head를 바꾸고 정렬하여 insert한다.
# 
# 

# In[14]:


import random
import string
import sys 

lim_num = 10000    ################### 수정 필요@@@@@@@@@@@@@@
year = [2013, 2014, 2015, 2016, 2017, 2018, 2019]
    
s = SinglyLinkedList()


#학생정보 생성 및 정렬하여 삽입하기
for i in range(lim_num):
    s.insert(''.join(random.choice(string.ascii_lowercase) for _ in range(10)), str(random.choice(year)) + ''.join(random.choice(string.digits) for _ in range(5)), "010-" + ''.join(random.choice(string.digits) for _ in range(4)) + "-" + ''.join(random.choice(string.digits) for _ in range(4)))

    

while True:
    print("학번순으로 출력하기 : 1")
    print("이름순으로 출력하기 : 2")
    option = input("메뉴선택(0 : 종료) : ")
    #학번순으로 정렬된 것을 출력
    if option == "1":
        s.display_by_stdnum()
    #이름순으로 정렬된 것을 출력
    elif option == "2":
        s.display_by_name()
    #탈출조건
    elif option == "0":
        print("종료합니다.")
        break
    #오류시
    else:
        print("잘못된 입력입니다.")


# In[11]:


'''단순 연결리스트 구현
    Node
    
    메서드들 : 
    size
    is_empty
    display
    insert_first
    insert
    insert_last
    delete
    delete_all
    replace
    is_in_list
    get_entry
    reverse
    concat
'''
class SinglyLinkedList:
    # Node : save the data
    class Node:
        def __init__(self, input_name, std_num, pho_num, name_next, std_next): 
            self.name = input_name
            self.std_num = std_num #학번
            self.pho_num = pho_num #전화번호
            self.name_next = name_next #이름전용 포인터
            self.std_next = std_next #학번전용 포인터
    
    def __init__(self):
        self.name_head = None
        self.std_head = None
        self.size = 0
        
    def size(self):
        return self.size
    
    def is_empty(self):
        if self.size == 0:
            return True
        else:
            return False

    def display_by_name(self):
        p = self.name_head
        count = 1
        if self.is_empty():
            print('empty - display by name')
            return None
        while p:
            if p.name_next != None:
                print(str(count) + ". " + p.name + " " + p.std_num + " " + p.pho_num + "\n")
            else:
                print(str(count) + ". " + p.name + " " + p.std_num + " " + p.pho_num + "\n")
            count += 1
            p = p.name_next
        
    def display_by_stdnum(self):
        q = self.std_head
        count = 1
        if self.is_empty():
            print('empty - display by stdnum')
            return None
        while q:
            if q.std_next != None:
                print(str(count) + ". " + q.name + " " + q.std_num + " " + q.pho_num + "\n")
            else:
                print(str(count) + ". " + q.name + " " + q.std_num + " " + q.pho_num + "\n")
            count += 1
            q = q.std_next
        
    def insert(self, name, std_num, pho_num):
        p = self.name_head
        q = self.std_head
        new_node = self.Node(name, std_num, pho_num, None, None)
        flag = True
        #when insert the first node
        if self.is_empty():
            self.name_head = new_node
            self.std_head = new_node
            self.size += 1
            return None
           
        #second node inserting, sorting by name
        if p.name_next == None:
            if p.name < name:
                p.name_next = new_node
            else:
                new_node.name_next = p
                self.name_head = new_node
        #second node inserting, sorting by stdnum      
        if q.std_next == None:
            if q.std_num < std_num:
                q.std_next = new_node
                self.size += 1
                return None
            else:
                new_node.std_next = q
                self.std_head = new_node
                self.size += 1
                return None
                    
        #from third node~
        while flag: #flag의 이유 : 이름으로 정렬이 우선 이루어지고 그 이후에 while문 탈출해서 학번으로 정렬을 이루도록.
            if p.name > name: #check first node
                new_node.name_next = p
                self.name_head = new_node
                break
            else:
                while p.name_next: #go to the end point
                    if p.name_next.name < name: #pass
                        p = p.name_next
                    else:
                        temp = p.name_next
                        new_node.name_next = temp
                        p.name_next = new_node 
                        flag = False
                        break
                if flag == False:
                    break
                #last point
                p.name_next = new_node
            break
        
        #from here sorting by stdnum
        if q.std_num > std_num:
            new_node.std_next = q
            self.std_head = new_node
            self.size += 1 
            return None
        else:
            while q.std_next: #go to the end point
                if q.std_next.std_num < std_num: #pass
                    q = q.std_next
                else:
                    temp2 = q.std_next
                    new_node.std_next = temp2
                    q.std_next = new_node
                    self.size += 1
                    return None
            q.std_next = new_node
                
        self.size += 1
        return None

        
        
        

