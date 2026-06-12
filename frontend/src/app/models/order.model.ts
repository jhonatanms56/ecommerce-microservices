export interface CreateOrderRequest{
  customerId: string;
  items: OrderItemRequest[];
 }

export interface OrderItemRequest {
  productId: string;
  productName: string;
  quantity: number;
  unitPrice: number;
}

// What you get back from GET /orders
export interface OrderResponse {
  id: string;
  customerId: string;
  status: 'PENDING' | 'CONFIRMED' | 'SHIPPED' | 'DELIVERED' | 'CANCELLED';
  totalAmount: number;
  createdAt: string;
  items: OrderItemResponse[];
}

export interface OrderItemResponse {
  productId: string;
  productName: string;
  quantity: number;
  unitPrice: number;
  totalPrice: number;
}
