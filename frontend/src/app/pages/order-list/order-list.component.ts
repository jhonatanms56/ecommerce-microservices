import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { OrderService } from '../../core/services/order.service';
import { OrderResponse } from '../../models/order.model';

@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatChipsModule,
    MatIconModule
  ],
  templateUrl: './order-list.component.html',
  styleUrl: './order-list.component.scss'
})
export class OrderListComponent {
  customerId = '';
  orders = signal<OrderResponse[]>([]);
  loading = signal(false);
  error = signal('');
  searched = signal(false);

  displayedColumns = ['id', 'customerId', 'status', 'totalAmount', 'createdAt'];

  constructor(private orderService: OrderService) {}

  searchOrders(): void {
    if (!this.customerId.trim()) return;

    this.loading.set(true);
    this.error.set('');
    this.orders.set([]);
    this.searched.set(false);

    this.orderService.getOrdersByCustomer(this.customerId).subscribe({
      next: (data) => {
        this.orders.set(data);
        this.loading.set(false);
        this.searched.set(true);
      },
      error: () => {
        this.error.set('Failed to load orders. Please try again.');
        this.loading.set(false);
      }
    });
  }

  getStatusColor(status: string): string {
    const colors: Record<string, string> = {
      PENDING: 'accent',
      CONFIRMED: 'primary',
      SHIPPED: 'primary',
      DELIVERED: '',
      CANCELLED: 'warn'
    };
    return colors[status] ?? '';
  }
}
