import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreateOrderRequest, OrderResponse } from '../../models/order.model';

@Injectable({
  providedIn: 'root'  // makes this service available app-wide, no need to declare it anywhere
})
export class OrderService {

  private apiUrl = '/orders';  // nginx will proxy this to order-service:8081

  constructor(private http: HttpClient) {}

  getOrdersByCustomer(customerId: string): Observable<OrderResponse[]> {
    return this.http.get<OrderResponse[]>(`${this.apiUrl}/customer/${customerId}`);
  }

  getOrderById(id: string): Observable<OrderResponse> {
    return this.http.get<OrderResponse>(`${this.apiUrl}/${id}`);
  }

  createOrder(request: CreateOrderRequest): Observable<OrderResponse> {
    return this.http.post<OrderResponse>(this.apiUrl, request);
  }
}
